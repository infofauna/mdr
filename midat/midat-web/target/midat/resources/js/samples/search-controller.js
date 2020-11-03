samplesApp.controller('samplesSearchCtrl', ["$scope","$location", "$http","$timeout", "$q", "dateFilter", "State", "config", "bioticIndexGradesService", "samplesService", "securityService", "commonSearchLogic", "PagingService", "Download",
function ($scope, $location, $http, $timeout, $q, dateFilter, State, config, bioticIndexGradesService, samplesService, securityService, commonSearchLogic, PagingService, Download) {


    $scope.locale = JECI.lang;
    $scope.searchResultTplUrl = config.searchResultTplUrl;
    $scope.routePath = $location.path;
    $scope.samplesUrl=config.samplesUrl;
    $scope.detailsBackPath="/search";
    $scope.batchUpdate = { fieldName : "published", value : "true"};

    var requestCanceller;
    createOrRestoreState();

    //common logic
    commonSearchLogic.defineCommonLogic($scope, config, search, buildSearchUrl, samplesService, securityService, bioticIndexGradesService);

    populateSelects();
    setupDatePickers();
    setupAutocompleteNames();

    function createOrRestoreState(){

        $scope.searchState = State.getCachedState("samplesSearchState");

        if(!$scope.searchState) {

            $scope.searchState = {
                search : false,
                resultUrl : null,
                selectedItems : [],
                searchCriteria : {
                    selectedGroup: null,
                    published : null
                },
                tableState : {},
                showSearchResult : false
            };

            State.addState("samplesSearchState", $scope.searchState);
        }else{
            //the datepicker freaks out if the dates are given as Date objects so we format them to string
            if($scope.searchState.searchCriteria.minDate){
                $scope.searchState.searchCriteria.minDate =$scope.searchState.searchCriteria.minDate;// dateFilter($scope.searchState.searchCriteria.minDate, "dd.MM.yyyy");
            }
            if($scope.searchState.searchCriteria.maxDate){
                $scope.searchState.searchCriteria.maxDate =$scope.searchState.searchCriteria.maxDate;//dateFilter($scope.searchState.searchCriteria.maxDate, "dd.MM.yyyy");
            }
        }
    }

    function populateSelects() {
        securityService.getGroups(function (groups) {
            $scope.groupsForSearch = groups.slice(0);
        });

        $http.get(config.indexTypesUrl).success(function (data) {
            $scope.indexTypesOptions = data;
            $scope.searchState.searchCriteria.indexType = $scope.indexTypesOptions[0].value;
        });
    }

    function setupDatePickers() {
        $scope.openMinDatePicker = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.minDatePickerOpened = true;
        };
        $scope.openMaxDatePicker = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.maxDatePickerOpened = true;
        };
    }

    function setupAutocompleteNames() {
        $scope.getWatercourseNamesLike = function (query) {
            return $http.get(config.watercourseAutocompleteUrl + query, {})
                .then(function (res) {
                    return res.data;
                });
        };
        $scope.getLocalityNamesLike = function (query) {
            return $http.get(config.localityAutocompleteUrl + query, {})
                .then(function (res) {
                    return res.data;
                });
        };



        //getStationNumbersLike
        $scope.getStationNumbersLike = function (query) {
            return $http.get(config.stationNumberAutocompleteUrl + query, {})
                .then(function (res) {
                    return res.data;
                });
        };

        //getGewissNumbersLike
        $scope.getGewissNumbersLike = function (query) {
            return $http.get(config.gewissNumberAutocompleteUrl + query, {})
                .then(function (res) {
                    return res.data;
                });
        };

        //principal institution
        $scope.getPrincipalInstitutionNamesLike = function (query) {
            return $http.get(config.principalInstitutionAutocompleteUrl + query, {})
                .then(function (res) {
                    return res.data;
                });
        };

        //project
        $scope.getProjectNamesLike = function (query) {
            return $http.get(config.projectNamesAutocompleteUrl + query, {})
                .then(function (res) {
                    return res.data;
                });
        };

    }

    //JIRA MIDAT / MID-258 Formulaire de recherche : Ajouter un bouton "clear"
     $scope.onResetClicked = function(){

         $scope.searchState.searchCriteria.minDate = new Date();
         $scope.searchState.searchCriteria.maxDate = new Date();

         $scope.searchState.searchCriteria.watercourse = null;
         $scope.searchState.searchCriteria.locality = null;
         $scope.searchState.searchCriteria.indexType = $scope.indexTypesOptions[0].value ;
         $scope.searchState.searchCriteria.minDate = null;
         $scope.searchState.searchCriteria.maxDate = null;
         $scope.searchState.searchCriteria.published = null;
         $scope.searchState.searchCriteria.selectedGroup = null;
         $scope.searchState.searchCriteria.selectedProtocolType = null;
         $scope.searchState.searchCriteria.minBioIndex = null;
         $scope.searchState.searchCriteria.maxBioIndex = null;
         $scope.searchState.searchCriteria.editionMode = false;


         //Add 4  search inputs
         $scope.searchState.searchCriteria.stationNumber = null;
         $scope.searchState.searchCriteria.project = null;


         $scope.searchState.searchCriteria.gewissNumber = null;
         $scope.searchState.searchCriteria.principalInstitution = null;

     }

    function search(){
        if($scope.searchState.search){
            cancelOngoingSearchRequest();
            $scope.searchUrlWithParams = buildSearchUrl();
            doSearchRequest();
        }else{
            $scope.searchState.searchResult = {
                rows : []
            };
        }
    }

    function cancelOngoingSearchRequest(){
        if(requestCanceller){
            requestCanceller.resolve("Request cancelled to avoid concurrent requests.");
        }
    }

    function buildSearchUrl(forExport){

        var url = (forExport ? (config.exportExcelModeEnabled === true ? config.exportExcelUrl: config.exportUrl) : config.searchUrl) + "?";

        if(!forExport){
            url += PagingService.buildPagingUrlParams($scope.searchState.tableState.page + 1, $scope.searchState.tableState.pageSize, $scope.searchState.tableState.orderBy, $scope.searchState.tableState.sortOrder);
        }

        return url;
    }

    function doSearchRequest(){

        //promise that can be used to cancel the request
        requestCanceller = $q.defer();

        $http.post($scope.searchUrlWithParams, $scope.searchState.searchCriteria, {timeout : requestCanceller.promise}).success(function(data){

            $scope.searchState.searchResult = data;
            $scope.searchState.tableState.totalRows=data.total;
            $scope.searchState.selectedItems.length=0;

            //artificially force a change to the rows so that the table updates the rows even if the object contain the same values
            //we need the new object references for the programmatic selection
            //this forces angular dirty checking to trigger the binding update
            //without this we have trouble with the selection (select all)
            angular.forEach(data.rows, function(row){
                row.tag = Date.now();
            });
        });
    }

    $scope.downloadExportCSVStatus = 1; // 1 NOT IN PROGRESS, 0 IN PROGRESS
    $scope.downloadExportCSV = function(event, pageCode, isFull){
        event.preventDefault();
        console.info("pageCode :"+pageCode);
        var url = buildSearchUrl(true);
            url+= "pageCode=" +pageCode;
            url+= "&isFull=" +isFull;

        console.log("Search-controller downloadExportCSV -export url :"+url);
        //promise that can be used to cancel the request
        var downloadPromise = $http.post(url, $scope.searchState.searchCriteria, {timeout : config.downloadExportCSVTimeout, responseType: 'blob'});
        downloadPromise.then(function(response){
            console.info("Search-controller downloadExportCSV sucess");
            Download.openAsFile(response);
            $scope.downloadExportCSVStatus = 1;
        },function(reject){
            console.error("Search-controller downloadExportCSV reject error");
            $scope.downloadExportCSVStatus = 1;
        } ).catch(function(error){
            console.error("Search-controller downloadExportCSV error");
            $scope.downloadExportCSVStatus = 1;
        });
        $scope.downloadExportCSVStatus =0;
    };
}]);
