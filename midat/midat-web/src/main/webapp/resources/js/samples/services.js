"use strict";

var midatSamplesApp = angular.module('samplesApp');

midatSamplesApp.factory('State', [function () {
        var service = {};

        service.cache = {};

        service.addState = function(key, value){
            if(!angular.isDefined(this.cache[key])){
                log("State service", "Adding new cached state", key);
            }else{
                log("State service", "WARNING : Cached state with this key already exists. it will be overwritten.", key);
            }
            this.cache[key] = value;
        };

        service.getCachedState = function(key){
            log("State service", "Getting cached state for key", key);

            if(angular.isDefined(this.cache[key])){
                log("State service", "Cached state for key found", key);
                return this.cache[key];
            }else{
                log("State service", "Cached state for key not found", key);
                return null;
            }
        };

        service.deleteState = function(key){
            log("State service", "Deleting state for key", key);
            if (angular.isDefined(this.cache[key])) {
                delete this.cache[key];
            }
        };

        return service;
}]);

midatSamplesApp.factory('PagingService', [function(){

    return {

        buildPagingUrlParams: function(page, pageSize, orderBy, sortOrder){

            var paramsStrings = [];

            paramsStrings.push('page=' + page);
            paramsStrings.push('pageSize=' + pageSize);
            paramsStrings.push('orderBy=' + orderBy);
            paramsStrings.push('sortOrder=' + sortOrder);

            return paramsStrings.join('&');
        }
    }
}]);

midatSamplesApp.factory("bioticIndexGradesService", ["$http", "config", "$q", function($http, config, $q){

    var values = {};

    var promise = $q.all([
        $http.get(config.bioticIndexGradesUrl+"IBCH").then(function(response){
            values.IBCH = response.data;
        }),
        $http.get(config.bioticIndexGradesUrl+"SPEAR").then(function(response){
            values.SPEAR = response.data;
        }),
        $http.get(config.bioticIndexGradesUrl+"MAKROINDEX").then(function(response){
            values.MAKROINDEX = response.data;
        })
    ]);

    return  {
        promise: promise,
        values : values
    };

}]);

midatSamplesApp.factory("samplesService", ["$http", "config", "$q", function($http, config, $q){

    return {

        batchDelete : function(sampleIds, successCallback){

            var confirmMessage = $j18n("search.resulttable.batchdelete.confirm");
            if(!window.confirm(confirmMessage)){
                return;
            }

            log("Deleting samples with IDs="+sampleIds);

            $http.delete(config.samplesUrl+sampleIds.join(",")).success(function(result){

                log("Batch delete OK");
                var message = $j18n("search.resulttable.batchdelete.success");
                noty({text: message, type: "success"});
                successCallback();

            });
        },

        batchUpdate : function(sampleIds, fieldName, value, successCallback){

            $http({
                method: 'POST',
                url: config.samplesUrl+sampleIds.join(","),
                data: $.param({fieldName: fieldName, value: value}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function(){
                log("Batch update OK")
                var message = $j18n("search.resulttable.batchedit.success");
                noty({text: message, type: "success"});
                successCallback();
            });
        },

        getAuditingInfo : function(sampleId, successCallback){

            var url = config.samplesAuditingInfoUrl.replace("{sampleId}", sampleId);
            $http({
                method: 'GET',
                url: url
            }).success(function(data){
                successCallback(data);
            });
        }

    };
}]);

midatSamplesApp.factory("securityService", ["$http", "config", "$q", function($http, config, $q){

    var groups = null;
    var sessionUser = null;

    var service = {
        getGroups : function(afterSuccess){
            if(groups){
                afterSuccess(groups);
            }else {

                $http.get(config.groupsForSearchUrl, {})
                    .then(function (res) {
                        groups = res.data;
                        afterSuccess(groups);
                    });
            }
        },

        getSessionUser : function(afterSuccess){
            if(sessionUser){
                afterSuccess(sessionUser);
            }else{

                $http.get(config.sessionUserUrl, {})
                    .then(function (res) {
                        sessionUser = res.data;

                        sessionUser.isPermitted = function(permission){
                            return sessionUser.permissions.indexOf(permission)!== -1;
                        };

                        afterSuccess(sessionUser);
                    });
            }
        }
    };
    return service;
}]);

midatSamplesApp.factory('httpErrorHandler', ['$q', function($q) {
    return {

        // --- Response interceptor for handling errors generically ---
        responseError: function(rejection) {

            //special case : 401 Unauthorized, probably session expired, redirect to login page
            if(rejection.status == 401){
                alert("A server request was made to an unauthorized source. Most probably, your session has expired.\n\nYou will now be redirected to the login page.");
                window.location=JECI.appRoot+"/authentication/login";
                return $q.reject(rejection);
            }

            //try to get error object in JSON from response body
            if(rejection.data && rejection.data.message && rejection.data.type){
                var simpleMessage = "A request to the server generated an error : \n\n"+rejection.data.message+"\n\nShow error details ?";
                if(confirm(simpleMessage)){
                    var detailedMessage = "Error code : "+rejection.status
                        +"\nError type : "+rejection.data.type
                        +"\n\nMessage : "+rejection.data.message
                        +"\n\n Trace :\n";

                    var trace = rejection.data.cause.stackTrace;
                    for(var i=0; i<10 || i<trace.length; i++){
                        var call = trace[i];
                        detailedMessage += "\n" + call.className +"." + call.methodName + " in file "+ call.fileName +" line " + call.lineNumber;
                    }

                    if(trace.length > 10){
                        detailedMessage +="\n.....\n\n " + (trace.length-10) + " calls omitted."
                    }

                    alert(detailedMessage);
                }
            }else{

            }

            return $q.reject(rejection);
        }
    };
}]);


/** add chart service **/

midatSamplesApp.factory("statisticsService", ["$http", "config", "$q", function($http, config, $q){

    var sampleProtocolPerMonth = null;
    var sampleProtocolPerUserCanton = null;
    var usersConnectionHistory=null
    var sampleIndexHistory=null;

    var service = {
        getSampleProtocolPerMonth : function(afterSuccess){
            if(sampleProtocolPerMonth){
                afterSuccess(sampleProtocolPerMonth);
            }else {

                $http.get(config.sampleProtocolPerMonthUrl, {})
                    .then(function (res) {
                        sampleProtocolPerMonth = res.data;
                        afterSuccess(sampleProtocolPerMonth);
                    });
            }
        },
        getSampleIndexHistory : function(afterSuccess){
            if(sampleIndexHistory){
                afterSuccess(sampleIndexHistory);
            }else {

                $http.get(config.sampleIndexHistoryUrl, {})
                    .then(function (res) {
                        sampleIndexHistory = res.data;
                        afterSuccess(sampleIndexHistory);
                    });
            }
        },

        getSampleProtocolPerUserCanton : function(afterSuccess){
            if(sampleProtocolPerUserCanton){
                afterSuccess(sampleProtocolPerUserCanton);
            }else{

                $http.get(config.sampleProtocolPerUserCantonUrl, {})
                    .then(function (res) {
                        sampleProtocolPerUserCanton = res.data;
                        afterSuccess(sampleProtocolPerUserCanton);
                    });
            }
        },
        getUsersConnectionHistory : function(afterSuccess){
            if(usersConnectionHistory){
                afterSuccess(usersConnectionHistory);
            }else{

                $http.get(config.usersConnectionHistoryUrl, {})
                    .then(function (res) {
                        usersConnectionHistory = res.data;
                        afterSuccess(usersConnectionHistory);
                    });
            }
        }
    };
    return service;
}]);


midatSamplesApp.factory('Download', [function() {
    return {
        openAsFile : function(response){

            // parse content type header
            var contentTypeStr = response.headers('Content-Type');
            var tokens = contentTypeStr.split('/');
            var subtype = tokens[1].split(';')[0];
            var contentType = {
                type : tokens[0],
                subtype : subtype
            };

            // parse content disposition header, attempt to get file name
            var contentDispStr = response.headers('Content-Disposition');
            var proposedFileName = contentDispStr ? contentDispStr.split('"')[1] : 'data.'+contentType.subtype;

            // build blob containing response data
            var blob = new Blob([response.data], {type : contentTypeStr});

            if (typeof window.navigator.msSaveBlob !== 'undefined'){
                // IE : use proprietary API
                window.navigator.msSaveBlob(blob, proposedFileName);
            }else{
                var downloadUrl = URL.createObjectURL(blob);

                // build and open link - use HTML5 a[download] attribute to specify filename
                var a = document.createElement("a");

                // safari doesn't support this yet
                if (typeof a.download === 'undefined') {
                    window.open(downloadUrl);
                }

                var link = document.createElement('a');
                link.href = downloadUrl;
                link.download = proposedFileName;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        }
    }
}]);
