samplesApp.service('commonSearchLogic', function () {
    return {
        defineCommonLogic: function($scope, config, searchFunction, searchUrlBuilder, samplesService, securityService, bioticIndexGradesService){

            $scope.indexesPopoverTplUrl = config.indexRatingPopoverTplUrl;
            $scope.auditPopoverTplUrl = config.auditPopoverTplUrl;



            $scope.onSearchClicked = function(){
                $scope.searchState.search=true;
                $scope.resetTableState();
                searchFunction($scope.searchState.searchType);
            };

            $scope.resetTableState = function(){
                $scope.searchState.tableState={
                    page:0,
                    orderBy:"sampleDate",
                    orderByReverse:true,
                    pageSize:10,
                    sortOrder:"asc",
                    totalRows : 0
                };
            };


            securityService.getSessionUser(function(sessionUser){
                $scope.sessionUser = sessionUser;

                if(sessionUser &&  sessionUser.permissions && sessionUser.permissions.length>0){
                    //managerObject = sessionUser.permissions.indexOf('midat:manager') > -1;
                    //added to check  the noexport role
                    noExportObject = sessionUser.permissions.indexOf('midat:export') === -1;

                    if(noExportObject){
                        // case user doesn't have the right to export data
                        console.log(">>>> NB. Current logged user  doesn't have the right to export data  <<<<<<<<<<<");
                        $scope.maxRows = 0;
                    }else{
                        if(sessionUser.permissions.indexOf('midat:manager') > -1){
                            console.log("User has midat:manager role");
                            $scope.maxRows = 500;
                        }else{
                            console.log("User doesn't have midat:manager role");
                            $scope.maxRows = 50;
                        }
                    }

                    console.log("maxRows allowed for MS Export is  :"+$scope.maxRows);
                }
            });




            $scope.bioticIndexQualityValues = bioticIndexGradesService.values;



            $scope.selectAllRows = function(){

                var selectedItems = $scope.searchState.selectedItems;

                angular.forEach($scope.searchState.searchResult.rows, function(row){
                    // add only if not already selected
                    if(selectedItems.indexOf(row) < 0){
                        selectedItems.push(row);
                    }
                });

                console.log("Selected all");
            };

            $scope.unselectAllRows = function(){
                $scope.searchState.selectedItems.length = 0;
            };




            $scope.showExportWarning = function(){
                $scope.exportWarning =1;
                setTimeout(function() {
                    $scope.exportWarning = 0;
                    console.log('message:' + $scope.exportWarning);
                    $scope.$apply(); //this triggers a $digest
                }, 5000);

            };


            $scope.strReplace= function (input, from, to) {
                input = input || '';
                from = from || '';
                to = to || '';
                return input.replace(new RegExp(from, 'g'), to);
            };

            $scope.unselectAllRows = function(){
                $scope.searchState.selectedItems.length = 0;
            };



            $scope.batchUpdateButtonClicked = function(){
                var ids = $scope.searchState.selectedItems.map(function(item){
                    return item.sampleId;
                });

                samplesService.batchUpdate(ids, $scope.batchUpdate.fieldName, $scope.batchUpdate.value, function(){
                    searchFunction($scope.searchState.searchType);
                });
            };

            $scope.batchDeleteButtonClicked = function(){
                var ids = $scope.searchState.selectedItems.map(function(item){
                    return item.sampleId;
                });

                samplesService.batchDelete(ids, function(){
                    searchFunction($scope.searchState.searchType);
                });
            };


            $scope.$watch("searchState.searchCriteria.editionMode", function(newValue, oldValue) {
                if($scope.searchState.searchCriteria
                    && ($scope.searchState.searchCriteria.editionMode === true || $scope.searchState.searchCriteria.editionMode === false)){
                    searchFunction($scope.searchState.searchType);
                }
            });

            $scope.$watch("searchState.searchCriteria.editionModeCarte", function(newValue, oldValue) {
                if($scope.searchState.searchCriteria){
                    if(newValue != oldValue){
                        $scope.searchState.searchType = "map";
                        searchFunction( $scope.searchState.searchType );
                    }
                }
            });

            $scope.onNextPageRequested = function (currentPage, filterBy, filterByFields, orderBy, orderByReverse) {
                if($scope.searchState.search) {

                    var sortOrder = orderByReverse ? "desc" : "asc";

                    if(currentPage != $scope.searchState.tableState.page || sortOrder != $scope.searchState.tableState.sortOrder){
                        $scope.searchState.tableState.page = currentPage;
                        $scope.searchState.tableState.sortOrder = sortOrder;

                        searchFunction($scope.searchState.searchType);
                    }
                }
            };

            $scope.loadCSVExport = function(){
                var url = searchUrlBuilder(true, $scope.searchState.searchType);
                window.open(url, "_blank");
            };

            $scope.loadAuditingData = function(sampleId){
                samplesService.getAuditingInfo(sampleId, function(auditingInfo){
                    $scope.auditingInfo = auditingInfo;
                });
            };



        }
    };
});
