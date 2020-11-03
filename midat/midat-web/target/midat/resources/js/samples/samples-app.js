"use strict";

var samplesApp = angular.module('samplesApp', [
    "ngRoute",
    "trNgGrid",
    "ngSanitize",
    "jeciFilters",
    "chart.js",
    "ui.bootstrap",
    "mgcrea.ngStrap",
    "geoadminMap",
    "ui.multiselect",
    "rzModule"
])

.config(["$routeProvider","$httpProvider",
    function ($routeProvider, $httpProvider) {
        $routeProvider.when("/search", {
            templateUrl: JECI.appRoot + "/resources/partials/samples/search.html",
            controller : "samplesSearchCtrl",
            resolve : {
                "bioticIndexesQualityData" : function(bioticIndexGradesService){
                    return bioticIndexGradesService.promise;
                }
            }
        }).
        when("/map", {
            templateUrl: JECI.appRoot + "/resources/partials/samples/map.html",
            controller : "samplesMapCtrl",
            resolve : {
                "bioticIndexesQualityData" : function(bioticIndexGradesService){
                    return bioticIndexGradesService.promise;
                }
            }
        }).
        when("/map/:param1", {
            templateUrl: JECI.appRoot + "/resources/partials/samples/map.html",
            controller : "samplesMapCtrl",
            resolve : {
                "bioticIndexesQualityData" : function(bioticIndexGradesService){
                    return bioticIndexGradesService.promise;
                }
            }
        }).
        when("/chart", {
            templateUrl: JECI.appRoot + "/resources/partials/samples/chart.html",
            controller : "samplesChartCtrl",
            resolve : {

            }
        }).
        when("/users", {
            templateUrl: JECI.appRoot + "/resources/partials/samples/connection.html",
            controller : "userConnectionCtrl",
            resolve : {

            }
        }).
        when("/details/:sampleId", {
            templateUrl: JECI.appRoot + "/resources/partials/samples/details.html",
            controller : "sampleDetailsCtrl",
            resolve : {
                "bioticIndexesQualityData" : function(bioticIndexGradesService){
                    return bioticIndexGradesService.promise;
                }
            }
        }).otherwise({
            redirectTo : "/search"
        });

        //use custom global error handling for ajax requests
        $httpProvider.interceptors.push('httpErrorHandler');

        //initialize get if not there
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }
        //disable IE ajax request caching
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    }
])



    .value("config", {
    searchResultTplUrl :        JECI.appRoot+ "/resources/partials/samples/search-result.html",
    groundProtocolItemTplUrl :  JECI.appRoot+ "/resources/partials/samples/ground-protocol-item.html",

    indexTabTemplatePathUrl:    JECI.appRoot+ "/resources/partials/samples/index-tab-tmpl.html",
    indexHistoryTemplatePathUrl:JECI.appRoot+ "/resources/partials/samples/index-history-tab-tmpl.html",

    indexRatingPopoverTplUrl :  JECI.appRoot+ "/resources/partials/samples/biotic-index-ratings.html",
    auditPopoverTplUrl :        JECI.appRoot+ "/resources/partials/auditing-info.html",
    searchUrl :                 JECI.appRoot+ "/api/midat-search",
    exportUrl :                 JECI.appRoot+ "/api/samples/export",
    mapSearchUrl :              JECI.appRoot+ "/api/midat-search/inside-rectangle",
    stationSearchUrl :          JECI.appRoot+ "/api/midat-search/station/",
    mapSearchUrlExport :        JECI.appRoot+ "/api/samples/export/inside-rectangle",
    stationSearchUrlExport :    JECI.appRoot+ "/api/samples/export/station/",
    samplesUrl :                JECI.appRoot+ "/api/samples/",
    sampleimportlogUrl :        JECI.appRoot+ "/api/samples/importlog/",
    sampleInfoIbchDataUrl :     JECI.appRoot+ "/api/samples/sampleInfoIbchData/",


    sampleIndiceHistoryDataUrl :     JECI.appRoot+ "/api/samples/sampleIndiceHistoryData/",

    samplesAuditingInfoUrl :    JECI.appRoot+ "/api/samples/{sampleId}/auditing-info",
    indexTypesUrl :             JECI.appRoot+ "/api/options/midat-index-types",
    watercourseAutocompleteUrl: JECI.appRoot+ "/api/stations/watercourse-names-like/",
    localityAutocompleteUrl :   JECI.appRoot+ "/api/stations/locality-names-like/",
    stationsGeoDataUrl :        JECI.appRoot+ "/api/stations/geodata",
    stationsGeoDataV2Url :      JECI.appRoot+ "/api/stations/geodata/v2",
    bioticIndexGradesUrl :      JECI.appRoot+ "/api/biotic-water-quality/",
    groupsForSearchUrl :        JECI.appRoot+ "/api/groups/user-groups-for-samples-search",
    sessionUserUrl :            JECI.appRoot+ "/api/users/session-user",
    intervalJsonUrl :           JECI.appRoot+ "/api/samples/interval/0",
    samplefiltersUrl:           JECI.appRoot+ "/api/stations/filters",

    additionalDataFormUrl:      JECI.appRoot+ "/app/import/lab/{sampleId}/additional",

    pageInfo :                  {
                                    "search" : {
                                        code : "MIDAT-030",
                                        title : "menu.searchForm"
                                    },
                                    "map" : {
                                        code : "MIDAT-031",
                                        title : "menu.searchGeo"
                                    },
                                    "details" : {
                                        code : "MIDAT-032",
                                        title : "menu.sampleDetails"
                                    },
                                    "chart" : {
                                        code : "MIDAT-041",
                                        title : "menu.chart.samples"
                                    },
                                    "users" : {
                                        code : "MIDAT-042",
                                        title : "menu.chart.users"
                                    }
                                },

    datepickerConfig : "{'starting-day' : 1, 'show-weeks' : false, 'format':'dd.MM.yyyy'}",
    downloadExportCSVTimeout:3600000,
    exportExcelUrl :                 JECI.appRoot+ "/api/samples/export-excel",
    mapSearchUrlExportExcel :        JECI.appRoot+ "/api/samples/export-excel/inside-rectangle",
    stationSearchUrlExportExcel :    JECI.appRoot+ "/api/samples/export-excel/station/",
    exportExcelModeEnabled : true,

    stationLinkunlinkUrl :        JECI.appRoot+ "/api/stations/linkunlink",

    stationNumberAutocompleteUrl: JECI.appRoot+ "/api/stations/station-numbers-like/",
    gewissNumberAutocompleteUrl: JECI.appRoot+ "/api/stations/gewiss-numbers-like/",

    projectNamesAutocompleteUrl: JECI.appRoot+ "/api/search-project/project-names-like/",
    principalInstitutionAutocompleteUrl: JECI.appRoot+ "/api/search-institution/principal-institution-names-like/",

    sampleProtocolPerMonthUrl:JECI.appRoot+ "/api/samples/sampleProtocolPerMonth/",
    sampleProtocolPerUserCantonUrl:JECI.appRoot+ "/api/samples/sampleProtocolPerUserCanton/",
    usersConnectionHistoryUrl:JECI.appRoot+ "/api/samples/usersConnectionHistory/",
    sampleIndexHistoryUrl:JECI.appRoot+ "/api/samples/sampleIndexHistory/",

})

.run(["$rootScope", "$location", "config", "uibDatepickerPopupConfig", function($rootScope, $location, config, datepickerPopupConfig ){
    TrNgGrid.setUpI18n();

    $rootScope.$on('$locationChangeStart', function(event, newUrl, oldUrl){
        $rootScope.pageInfoLoaded=false;
    });

    $rootScope.$on('$locationChangeSuccess', function(event, newUrl, oldUrl){

        $rootScope.routeUrl = newUrl;
        var routeName = $location.path().split("/")[1];
        $rootScope.pageCode = config.pageInfo[routeName].code;
        $rootScope.pageTitle = config.pageInfo[routeName].title;
        $rootScope.pageInfoLoaded=true;
    });

    //translation for the datepicker popup buttons
    datepickerPopupConfig.currentText = $j18n("global.datepicker.popup.current");
    datepickerPopupConfig.clearText = $j18n("global.datepicker.popup.clear");
    datepickerPopupConfig.closeText = $j18n("global.datepicker.popup.close");
    datepickerPopupConfig.toggleWeeksText = $j18n("global.datepicker.popup.toggleweeks");

    //grid - number of pages from which all pages are not shown
    TrNgGrid.defaultPagerMinifiedPageCountThreshold = 10;


}]);
