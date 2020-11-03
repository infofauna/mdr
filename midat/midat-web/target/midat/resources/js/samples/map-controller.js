samplesApp.controller('samplesMapCtrl',
["$scope", "$http", "$routeParams","$timeout", "State", "config", "bioticIndexGradesService", "samplesService", "olStylesFactory", "securityService", "commonSearchLogic","Download",
function ($scope, $http,$routeParams, $timeout, State, config, bioticIndexGradesService, samplesService, olStylesFactory, securityService, commonSearchLogic,Download) {

    // new V2.0
    moment.locale(JECI.lang);

    var  DATE_0 = null;
    var  DATE_1 = null;
    var updateMapModelToMatchDateSelections = null;

    addDateSlider($routeParams.param1);

    var previousServerRequestPromise=null;
    $scope.searchResultTplUrl = config.searchResultTplUrl;

    createOrRestoreState();



    //common logic
    commonSearchLogic.defineCommonLogic($scope, config, search, buildSearchUrl, samplesService, securityService, bioticIndexGradesService);

    $scope.locale = JECI.lang;
    $scope.batchUpdate = { fieldName : "published", value : "false"};
    $scope.bioticIndexQualityValues = bioticIndexGradesService.values;
    $scope.bioticIndexQualityValuesExtended= getExtendedBioticIndexQualityValues(bioticIndexGradesService.values);
    $scope.searchState.searchCriteria.editionModeCarte = false;

    $scope.onStationSelected = function(stationId){
        if(stationId){
            $scope.searchState.search=true;
            $scope.searchState.selectedStationId=stationId;
            $scope.resetTableState();
            $scope.clearRectangleSelection();
            search("station");
            $scope.searchState.searchType = "station";
        }else{
            $scope.searchState.selectedStationId=null;
        }
    };


    function buildStationsLayer() {

        var filered = false;
        if($routeParams.param1){
            filered = true;
        }

        var geoJsonUrl = config.stationsGeoDataUrl + "?editionMode=" + $scope.searchState.searchCriteria.editionModeCarte+"&filered="+filered;
        var source = new ol.source.Vector({});
        var format = new ol.format.GeoJSON();

        var stationsLayer = new ol.layer.Vector({
            source: source,
            style: stationStyleFunction

        });

        setTimeout(function(){
            // show it after 0.5 second from request
            $('#loader-wrapper').removeClass('hidden');
        },0)

        //manually load the GeoJSON features into the layer
        //the documented way does not work. See http://stackoverflow.com/questions/30657032/openlayers-3-5-and-geojson
        $http.get(geoJsonUrl).success(function(data){
            console.log('remove loader');
            $('#loader-wrapper').removeClass('hidden');
            $('#loader-wrapper').addClass('hidden');
            setTimeout(function(){
                for(var i=0; i<data.features.length; i++){
                    var feature = format.readFeature(data.features[i]);
                    source.addFeature(feature);
                }
                $('#loader-wrapper').removeClass('hidden');
                $('#loader-wrapper').addClass('hidden');
            },0)
        });

        return stationsLayer;
    }

    $scope.startSelectionButtonClicked = function(){
        if($scope.searchState.mapModel.rectangleSelector.selectionMode == "off"){
            $scope.searchState.mapModel.rectangleSelector.selectionMode="on";

        }else{
            $scope.searchState.mapModel.rectangleSelector.selectionMode="off";
        }
    };

    $scope.clearRectangleSelection = function(){
        $scope.searchState.mapModel.rectangleSelector.clearSelection();
    };

    $scope.clearStationSelection = function(){
        $scope.onStationSelected(null);
        selectStationInteraction.getFeatures().clear();
    };

    $scope.clearAllSelections = function(){
        $scope.clearRectangleSelection();
        $scope.clearStationSelection();
        $scope.searchState.resultUrl=null;
    };

    $scope.getStartSelectionButtonLabel = function(){
        if($scope.searchState.mapModel.rectangleSelector.selectionMode=="on"){
            return $j18n("search.map.cancel");
        }else{
            return $j18n("search.map.newselection");
        }
    };

    $scope.getStartSelectionButtonBtnClass = function(){
        if($scope.searchState.mapModel.rectangleSelector.selectionMode=="on"){
            return "btn-danger";
        }else{
            return "btn-primary";
        }
    }

    function createOrRestoreState() {
        $scope.searchState = State.getCachedState("mapSearchState");

        if (!$scope.searchState) {
            $scope.searchState = {
                search: false,
                selectedItems : [],
                resultUrl: null,
                tableState: {},
                showSearchResult: false,
                searchType: "area",
                searchCriteria : {}
            };
            State.addState("mapSearchState", $scope.searchState);

        }
    }

    function search(searchType) {
        if( $scope.searchState.searchType === "map" ){
            $scope.searchState.searchType = null;
            cancelOngoingSearchRequest();
            $scope.clearAllSelections();
            resetTableResult();
            $scope.searchState.mapModel.additionalLayers = [buildStationsLayerV2($scope.indexType, $scope.dateSlider.minValue,$scope.dateSlider.maxValue)];
        }else{
            if ($scope.searchState.search) {
                cancelOngoingSearchRequest();
                $scope.searchUrlWithParams=buildSearchUrl(false, searchType);
                doSearchRequest();
            } else {
                $scope.searchState.searchResult = {
                    rows: []
                };
            }
        }

    }

    function cancelOngoingSearchRequest(){
        if (previousServerRequestPromise) {
            $timeout.cancel(previousServerRequestPromise);
            previousServerRequestPromise = null;
        }
    }

    function buildSearchUrl(forExport, searchType){
        var url;
        if(searchType === "area"){
            url = (forExport ? (config.exportExcelModeEnabled === true ?config.mapSearchUrlExportExcel:config.mapSearchUrlExport) : config.mapSearchUrl) + "?";

            //rectangle parameters
            var criteria = $scope.searchState.mapModel.rectangleSelector.rectangle;
            if($.isEmptyObject(criteria) && forExport){
                console.error("export is not possible due to criteria object is empty...");
                //export.xlsx.selection.empty
                alert($j18n("export.xlsx.selection.empty"));
                return;
            }

            for(name in criteria){
                var value = criteria[name];
                if(value) {
                    url += name + "=" + value + "&";
                }else{
                    log("The field "+name+" of the rectangle has no value !")
                }
            }
        }else{
            url = (forExport ? (config.exportExcelModeEnabled === true? config.stationSearchUrlExportExcel:config.stationSearchUrlExport ): config.stationSearchUrl) + $scope.searchState.selectedStationId +"?";
        }

        url += "editionMode=" + $scope.searchState.searchCriteria.editionModeCarte;

        //paging parameters
        if(!forExport) {
            url += "&page=" + ($scope.searchState.tableState.page + 1);
            url += "&pageSize=" + $scope.searchState.tableState.pageSize;
            url += "&orderBy=" + $scope.searchState.tableState.orderBy;
            url += "&sortOrder=" + $scope.searchState.tableState.sortOrder;
        }

        //add dates [start, end]
        if($scope.searchState.dateSliderMinValue){
            url += "&startDate=" + $scope.searchState.dateSliderMinValue;
        }else{
            url += "&startDate=" + DATE_0;
        }

        if($scope.searchState.dateSliderMaxValue){
            var newMaxValue= moment($scope.searchState.dateSliderMaxValue ,"MM-DD-YYYY").add(1,"M").format("MM-DD-YYYY");
            url += "&endDate=" + newMaxValue;
        }else{
            var newMaxValue= moment(DATE_1 ,"MM-DD-YYYY").add(1,"M").format("MM-DD-YYYY");
            url += "&endDate=" + newMaxValue;
        }




        return url;
    }

    function doSearchRequest(){
        previousServerRequestPromise = $http.get( $scope.searchUrlWithParams).success(function(data){
            previousServerRequestPromise = null;
            $scope.searchState.searchResult = data;
            $scope.searchState.tableState.totalRows=data.total;
        });
    }

    function stationStyleFunction(feature, resolution) {

        if(feature.get("stationColor")){

            var fillColor = feature.get("stationColor");
            if(resolution < 40){
                //var text = "N° " + feature.get("stationNumber")+ " : " + feature.get('watercourse') + ", " + feature.get('locality');
                var text = "N° " + feature.get("stationNumber")+ " : " + feature.get('locality'); //JIRA MID-257
                return [new ol.style.Style({
                    text : olStylesFactory.featureV2TextStyle(text,fillColor),
                    image : olStylesFactory.featureV2PointStyleBig(fillColor)
                })];
            }else{
                return [new ol.style.Style({
                    image : olStylesFactory.featureV2PointStyleSmall(fillColor)
                })];
            }
        }else{
        if(resolution < 40){
            //var text = "N° " + feature.get("stationNumber")+ " : " + feature.get('watercourse') + ", " + feature.get('locality');
            var text = "N° " + feature.get("stationNumber")+ " : " + feature.get('locality'); //JIRA MID-257
            return [new ol.style.Style({
                text : olStylesFactory.featureTextStyle(text),
                image : olStylesFactory.featurePointStyleBig
            })];
        }else{
            return [new ol.style.Style({
                image : olStylesFactory.featurePointStyleSmall
            })];
        }
        }
    };

    function stationStyleSelectedFunction(feature, resolution) {
        if(feature.get("stationColor")){
            var fillColor = feature.get("stationColor");
            if(resolution < 100){
                //var text = resolution < 100 ? "N° " + feature.get("stationNumber")+ " : " + feature.get('watercourse') + ", " + feature.get('locality') : "";
                var text = resolution < 100 ? "N° " + feature.get("stationNumber")+ " : " + feature.get('locality') : ""; //JIRA MID-257
                return [new ol.style.Style({
                    text : olStylesFactory.featureV2TextStyleSelected(text,fillColor),
                    image : olStylesFactory.featureV2PointStyleBigSelected(fillColor)
                })];
            }else{
                return [new ol.style.Style({
                    image : olStylesFactory.featureV2PointStyleSmallSelected(fillColor)
                })];
            }
        }else{
            if(resolution < 100){
                //var text = resolution < 100 ? "N° " + feature.get("stationNumber")+ " : " + feature.get('watercourse') + ", " + feature.get('locality') : "";
                var text = resolution < 100 ? "N° " + feature.get("stationNumber")+ " : " + feature.get('locality') : ""; //JIRA MID-257
                return [new ol.style.Style({
                    text : olStylesFactory.featureTextStyleSelected(text),
                    image : olStylesFactory.featurePointStyleBigSelected
                })];
            }else{
                return [new ol.style.Style({
                    image : olStylesFactory.featurePointStyleSmallSelected
                })];
            }
        }
    }

    function onRectangleSelected(){
        $scope.clearStationSelection();
        $scope.searchState.search=true;
        $scope.resetTableState();
        search("area");
        $scope.searchState.searchType = "area";
    };

    $scope.downloadExportCSVStatus = 1; // 1 NOT IN PROGRESS, 0 IN PROGRESS
    $scope.downloadExportCSV = function(event,pageCode,isFull){
        event.preventDefault();
        console.info("pageCode :"+pageCode);

        var url = buildSearchUrl(true, $scope.searchState.searchType);
            url+= "&pageCode=" +pageCode;
            url+= "&isFull=" +isFull;

        console.log("Map-controller downloadExportCSV -export url :"+url);
        //promise that can be used to cancel the request
        var downloadPromise = $http.get(url,{timeout : config.downloadExportCSVTimeout, responseType: 'blob'});
        downloadPromise.then(function(response){
            console.info("Map-controller downloadExportCSV sucess");
            Download.openAsFile(response);
            $scope.downloadExportCSVStatus = 1;
        },function(reject){
            console.error("Map-controller downloadExportCSV rejected");
            $scope.downloadExportCSVStatus = 1;
        } ).catch(function(error){
            console.error("Map-controller downloadExportCSV error");
            $scope.downloadExportCSVStatus = 1;
        });
        $scope.downloadExportCSVStatus =0;
    };

    /* V2.0 */

    function addDateSlider(filter){

        createDateSlider();

        if(filter && filter === "filtered" ){
          var data =getSyncFiltersData().responseJSON;
          $scope.samplesFilters = data
          console.log(" addDateSlider set  $scope.samplesFilters -->JSONfilters:"+JSON.stringify(data,null,3));
        }else{
            console.log(" addDateSlider set  $scope.samplesFilters to undefined ");
            $scope.samplesFilters= undefined;
        }
    }

    function checkIfShouldLoadMapModel(filter){
       // !$scope.searchState.mapModel || isFilteredAndNotLoaded($routeParams.param1,$scope.searchKey)

        if(filter && filter === "filtered" ){

            console.log(" ********** filter Case **************");

            //1. case Map Model not loaded
            if( !$scope.searchState.mapModel){
                console.log(" ***** filter Case 1. case Map Model not loaded****");
                $scope.searchState.searchKey =getSearchKey();

                //when a date filter is set we should put it as default date
                setDateSliderMinMaxValuesIfExist();

                return true ;
            }

            //2. Map Model was loaded with same filter
            if($scope.searchState.mapModel && $scope.searchState.searchKey == getSearchKey()){
                console.log(" ***** filter Case 2. Map Model was loaded with same filter****");
                return false;

            }

            //3. case Map Model was loaded with a different filter
            if($scope.searchState.mapModel && $scope.searchState.searchKey != getSearchKey()){
                console.log(" ***** filter Case 3. case Map Model was loaded with a different filter ****");
                $scope.searchState.searchKey =getSearchKey();

                //when a date filter is set we should put it as default date
                resetFilterValues();
                setDateSliderMinMaxValuesIfExist();

                return true ;
            }

        }else{
            console.log(" ********** Menu Case **************");
            //1. case Map Model not loaded
            if( !$scope.searchState.mapModel){
                console.log(" **** Menu Case 1. case Map Model not loaded *****");
                $scope.searchState.searchKey =null;
                return true ;
            }

            //2. Map Model was loaded without filter
            if($scope.searchState.mapModel && $scope.searchState.searchKey == null){
                console.log(" ***** Menu Case 2. Map Model was loaded without filter ****");
                return false;
            }

            //3. case Map Model was loaded with a filter
            if($scope.searchState.mapModel && $scope.searchState.searchKey != null){
                console.log(" **** Menu Case 3. case Map Model was loaded with a filter ****");
                $scope.searchState.searchKey =null;
                resetFilterValues();
                return true ;
            }
        }

    }



    function resetFilterValues(){
        if($scope.searchState){
            $scope.searchState.bioticIndexType    = null;
            $scope.searchState.dateSliderMinValue = null;
            $scope.searchState.dateSliderMaxValue = null;
        }
    }

    function getSyncFiltersData() {
        return $.ajax({
            type: "GET",
            url: config.samplefiltersUrl,
            async: false,
            success: function (result) {
                   /* if result is a JSon object */
                    return result;
                },error:function(err){
                    console.error(" getSyncFiltersData result is not valid JSON :"+err);
                     return [];
                }


        });
    }

    /*
    * get the searchKey in order to know if there is a need to relaod the mapModel or not .
    * */
    function getSearchKey(){
        if($scope.samplesFilters){
            var res= $.grep($scope.samplesFilters,function(rec){
                return  rec.codeName == "searchKey";
            });
            console.log(" getSearchKey -->searchKey filter:"+JSON.stringify(res,null,3));
            if(res.length>0){
                console.log(" getSearchKey -->searchKey res[0][value]:"+res[0]["value"]);
                return res[0]["value"];
            }else {
                return null;
            }
        }else{
            return null;
        }
    }


    /* set the dateSlider min and max date when filtered */
    function setDateSliderMinMaxValuesIfExist(){

        if($scope.samplesFilters){

            $scope.searchState.dateSliderMinValue = null;
            $scope.searchState.dateSliderMaxValue = null;


            var resMinDate= $.grep($scope.samplesFilters,function(rec){
                return  rec.codeName == "searchCriteria.minDate";
            });

            if(resMinDate.length>0){
                $scope.searchState.dateSliderMinValue =resMinDate[0]["value"];

            }
            //maxDate
            var resMaxDate= $.grep($scope.samplesFilters,function(rec){
                return  rec.codeName == "searchCriteria.maxDate";
            });

            if(resMaxDate.length>0){
                $scope.searchState.dateSliderMaxValue =resMaxDate[0]["value"];
            }


        }
    }

     function createDateSlider() {
        var intervalJsonUrl = config.intervalJsonUrl ;

        $http.get(intervalJsonUrl).success(function(data){

            //callback to make sure map model is updated
             var updateMapModelToMatchDateSelectionsFlag=null;


            if(data.length ==2 ){
                //Sort date interval in case not sorted .
               if(moment(data[1],"MM-DD-YYYY").isAfter(moment(data[0],"MM-DD-YYYY"))) {
                   data.reverse();
               }
                DATE_0=moment(data[1],"MM-DD-YYYY").format('MM-DD-YYYY');
                DATE_1=moment(data[0],"MM-DD-YYYY").format('MM-DD-YYYY');

                dateArray = getDates(data[1],data[0]);
                initDateSider(dateArray,data[1],data[0]);

                setTimeout(function(){
                    customiseLegend();
                }, 500)



                /* set the date filter restrictions whenever is  */
                if($scope.searchState.dateSliderMinValue  && $scope.searchState.dateSliderMinValue!=null){

                    //make sure that MinValue is >= min date =Data[1]
                   if(moment($scope.searchState.dateSliderMinValue,"MM-DD-YYYY").isAfter(moment(data[1],"MM-DD-YYYY"))){
                       $scope.dateSlider.minValue = $scope.searchState.dateSliderMinValue;
                       updateMapModelToMatchDateSelectionsFlag= true;

                   }else{
                       $scope.dateSlider.minValue = dateArray[0];
                   }
                }
                if($scope.searchState.dateSliderMaxValue  && $scope.searchState.dateSliderMaxValue!=null){
                    // $scope.searchState.maxDate =moment($scope.searchState.dateSliderMaxValue,"MM-DD-YYYY").format('yyyy-MM-dd');
                    //make sure that MaxValue is <= max date =Data[0]
                    if(moment($scope.searchState.dateSliderMaxValue,"MM-DD-YYYY").isBefore(moment(data[0],"MM-DD-YYYY"))){
                        $scope.dateSlider.maxValue = $scope.searchState.dateSliderMaxValue;
                        updateMapModelToMatchDateSelectionsFlag= true;
                    }else{
                        $scope.dateSlider.maxValue =dateArray[dateArray.length -1];
                    }
                }
                /* set the biotic index */
                if($scope.searchState.bioticIndexType  && $scope.searchState.bioticIndexType !=null){
                    $scope.indexType = $scope.searchState.bioticIndexType;
                }

                 //callback
                updateMapModelToMatchDateSelections=updateMapModelToMatchDateSelectionsFlag;
                console.log("createDateSlider updateMapModelToMatchDateSelections :"+updateMapModelToMatchDateSelections);


            }

        });

        $http.get(config.indexTypesUrl).success(function (data) {
         $scope.indexTypesOptions = data;
         $scope.indexTypesOptions = [{ "value": "", "label": "" }].concat($scope.indexTypesOptions);
         $scope.indexType = $scope.indexTypesOptions[0].value;
        });



    };

    function resetTableResult(){
        $scope.searchState.searchResult = [];
        $scope.searchState.tableState.totalRows=0
    }
     $scope.updateIndex = function() {
            $scope.clearAllSelections();
            resetTableResult();
            $scope.searchState.mapModel.additionalLayers =[buildStationsLayerV2($scope.indexType, $scope.dateSlider.minValue,$scope.dateSlider.maxValue)];
     }



      /*
        getPointerColor: function(value) {
            return '#2AE02A';
        },
        getSelectionBarColor: function(value) {
            return '#2AE02A';
        },
      */

     function initDateSider(dates,date0, date1){
         $scope.dateSlider = {
             minValue:dates[0],
             maxValue:dates[dates.length -1],
             options: {
                 id: 'slider-id',
                 floor: date0,
                 ceil:  date1,
                 draggableRange: true,
                 stepsArray: getDateLegend(dates),
                 showTicks:true,
                 showTicksValues: false,

                 translate: function(date) {
                     if (date != null){
                         return moment(date,"MM-DD-YYYY").format('MMM - YYYY ');
                     }
                     return '';
                 },
                 onStart: function(id,minValue,maxValue) {
                     $scope.clearAllSelections();
                     var newMaxValue= moment(maxValue,"MM-DD-YYYY").add(1,"M").format("MM-DD-YYYY");
                     console.log('on start: [' + minValue+'-'+newMaxValue+']'); // logs 'on start slider-id'
                 },
                 onChange: function(id,minValue,maxValue) {


                 },
                 onEnd: function(id,minValue,maxValue) {
                     $scope.searchState.mapModel.additionalLayers =[buildStationsLayerV2($scope.indexType,minValue,maxValue)];
                     var newMaxValue= moment(maxValue,"MM-DD-YYYY").add(1,"M").format("MM-DD-YYYY");
                     console.log('on end : [' + minValue+'-'+newMaxValue+']'); // logs 'on end slider-id'
                     //$scope.clearAllSelections();

                 }
             }
         }
     }



    function getDates(startDate, stopDate) {

        startDate = moment(startDate,"MM-DD-YYYY");
        stopDate=    moment(stopDate,"MM-DD-YYYY");

        var itr = moment.twix(startDate,stopDate).iterate("months");
        var range=[];
        while(itr.hasNext()){
            range.push(moment(itr.next().toDate()).format('MM-DD-YYYY'))
        }
       // console.log(range);

        return range;
    }

    $scope.formatDateForDisplay= function(date){
        return  moment(date,"MM-DD-YYYY").format('MMM - YYYY ');
    }

    function buildStationsLayerV2(index,startDate,endDate) {

            //stock filter values in order to rest the from
            $scope.searchState.bioticIndexType    = index;
            $scope.searchState.dateSliderMinValue = startDate;
            $scope.searchState.dateSliderMaxValue = endDate;


            var newMaxValue= moment(endDate,"MM-DD-YYYY").add(1,"M").format("MM-DD-YYYY");
            console.log('on change: [' + startDate+'-'+newMaxValue+']'); // logs 'on change slider-id'



            var geoJsonUrl = config.stationsGeoDataV2Url
                + "?editionMode=" + $scope.searchState.searchCriteria.editionModeCarte
                + "&index=" +index
                + "&startDate=" + startDate
                + "&endDate=" + newMaxValue;

            console.log("buildStationsLayerV2 url:"+geoJsonUrl);

            var source = new ol.source.Vector({});
            var format = new ol.format.GeoJSON();

            var stationsLayer = new ol.layer.Vector({
                source: source,
                style: stationStyleFunction

            });


            //manually load the GeoJSON features into the layer
            //the documented way does not work. See http://stackoverflow.com/questions/30657032/openlayers-3-5-and-geojson

            $http.get(geoJsonUrl).success(function(data){
                $('#loader-wrapper').removeClass('hidden');
                $('#loader-wrapper').addClass('hidden');
                for(var i=0; i<data.features.length; i++){
                    var feature = format.readFeature(data.features[i]);
                    source.addFeature(feature);
                }
            });
        return stationsLayer;

    }

 /* custom legend */
    function getDateLegend(dates){
        var stepsArray =[];
        //fill in the array
        dates.forEach(function(dateValue,dateIndex){
            // console.log("getDateLegend dateValue["+dateIndex+"]:"+dateValue);
            var stepVal ='';
            if(dateValue.startsWith('01-01')){
                stepVal =dateValue.substring(8);
            }else{
                var t =dateValue.substring(0,3);
                if(parseInt(t)  !=12 && parseInt(t) %6 ==0){
                   stepVal =parseInt(t);
                }
               //dateValue.substring(3,6);
            }
            var step ={value:dateValue, legend: stepVal.toString()}
            stepsArray.push(step)
        });


        return stepsArray;
    }

     function customiseLegend(){

         $('.rz-ticks li').each(function(i, li) {
             var step = $(li);
                   if(step.text().trim().length == 0 || step.text().trim().length == 1){
                       step.hide();
                   }else if(step.text().trim().length == 2) {
                       step.css("font-size", 11 + "px");
                       step.css("line-height", 1.5);

                   }
             }
         );
     }

    function getExtendedBioticIndexQualityValues(bioticIndexGradesValues){

        var checkIfAddedAlready = $.grep(bioticIndexGradesValues["MAKROINDEX"],function(rec){
            return  rec.ratingCode == "UNDIFINED";
        });

        if(checkIfAddedAlready.length == 0){
            var NOTCALCULATEDVAL={
                "indexTypeCode": "MAKROINDEX",
                "ratingCode": "UNDIFINED",
                "bgColorCode": "#808080",
                "textColorCode": "#000",
                "rangeLegendText": "MAKROINDEX "+$j18n("search.details.sample.notAvailable"),
                "sortOrder": 6,
                "fromValue": 0,
                "toValue": 0,
                "rating": $j18n("search.details.sample.notAvailable")
            }
            bioticIndexGradesValues["MAKROINDEX"].push(NOTCALCULATEDVAL);

        }
        return  $scope.bioticIndexQualityValues;
    }

    /** demo control **/

    var state = 'stop';

    $scope.buttonBackPress =function() {
        console.log("button back invoked.");
    }

    $scope.buttonForwardPress = function() {
        console.log("button forward invoked.");
    }

    $scope.buttonRewindPress=function() {
        console.log("button rewind invoked.");
    }

    $scope.buttonFastforwardPress =function() {
        console.log("button fast forward invoked.");
    }


    var timerArray =[];

    $scope.buttonPlayPress = function() {
        clearTimer();

        if(state=='play-reserve' || state=='resume-reserve' ||state=='pause-reserve'){
            $scope.buttonStopPress();
        }

        if(state=='stop'){
            state='play';
            $("#button_play").attr('class','btn btn-success');
            $("#button_play i").attr('class', "fa fa-pause");

            // setupDemo(minIntervalDate, maxIntervalDate,startDate, endDate,reversed)
            setupDemo($scope.dateSlider.minValue, DATE_1,$scope.dateSlider.minValue,$scope.dateSlider.minValue,false);
        }
        else if(state=='play' || state=='resume'){
            state = 'pause';
            $("#button_play i").attr('class', "fa fa-play");
        }
        else if(state=='pause'){
            state = 'resume';
            $("#button_play i").attr('class', "fa fa-pause");

            //setupDemo(minIntervalDate, maxIntervalDate,startDate, endDate,reversed)
            setupDemo($scope.dateSlider.maxValue, DATE_1,$scope.dateSlider.minValue,$scope.dateSlider.maxValue,false);
        }
        console.log("button play pressed, play was "+state);
   }

   $scope.buttonReversePlayPress = function(){
       clearTimer();

       if(state=='play' || state=='resume' ||state=='pause'){
           $scope.buttonStopPress();
       }
       if(state =='stop'){
           state ='play-reserve';
           $("#button_reverse").attr('class','btn btn-success');
           $("#button_reverse i").attr('class', "fa fa-pause");

           // setupDemo(minIntervalDate, maxIntervalDate,startDate, endDate,reversed)
           setupDemo($scope.dateSlider.minValue, DATE_1,$scope.dateSlider.minValue,DATE_1,true);
       }
       else if(state =="play-reserve" || state=="resume-reserve"){
           state = "pause-reserve";
           $("#button_reverse i").attr('class', "fa fa-play");
       }
       else if(state =="pause-reserve"){
           state = "resume-reserve";
           $("#button_reverse i").attr('class', "fa fa-pause");

           //setupDemo(minIntervalDate, maxIntervalDate,startDate, endDate,reversed)
           setupDemo($scope.dateSlider.minValue, $scope.dateSlider.maxValue,$scope.dateSlider.minValue,$scope.dateSlider.maxValue,true);
       }
       console.log("button reverse play pressed, play was "+state);
   }

    $scope.buttonStopPress= function(){
        clearTimer();
        state = 'stop';

        $("#button_play").attr('class','btn');
        $("#button_play i").attr('class', "fa fa-play");

        $("#button_reverse").attr('class','btn');
        $("#button_reverse i").attr('class', "fa fa-play");

        console.log("button stop invoked.");
    }

    var TIME_SLOT = 1000;
    function setupDemo(minIntervalDate, maxIntervalDate,startDate, endDate,reversed){
        timerArray =[];
        var dateArray_ =getDates(minIntervalDate, maxIntervalDate);

        if(reversed){
            dateArray_.reverse();
        }else{
            $scope.dateSlider.minValue =startDate;
            $scope.dateSlider.maxValue =endDate;
        }

        var stopTimer= $timeout(function () {
            $scope.buttonStopPress();
        },( (dateArray_.length +1) )*TIME_SLOT);
        timerArray.push(stopTimer);

        dateArray_.forEach(function(val, idx){
            var promise =$timeout(function () {
                $scope.dateSlider.maxValue =val;
                $scope.updateIndex();
            },(1+idx)*TIME_SLOT);
            timerArray.push(promise);
        });
    }
    function clearTimer(){
            timerArray.forEach(function(v,i){
                $timeout.cancel(v);
            });
    }

    $scope.resetTimer= function(){
        clearTimer();
    }

    $scope.$on('$destroy', function() {
        // clean up stuff
        console.log("clear timer on ctrl destroy ...");
        clearTimer();
    })


    var selectStationInteraction = new ol.interaction.Select({style: stationStyleSelectedFunction});

    selectStationInteraction.getFeatures().on('change:length', function(e) {
        if (e.target.getLength() === 0) {
            $scope.onStationSelected(null);
        } else {
            var feature = e.target.item(0);
            $scope.onStationSelected(feature.getId());
        }
    });


    // load the mapModel only if mapModel is not loaded or if the map is filtered and not loaded before
    if(checkIfShouldLoadMapModel($routeParams.param1)) {
        console.log("************** Load Map MOdel  **************");

        $scope.searchState.mapModel = {
            height: "450px",
            width: "100%",
            resolution: 520,
            showMarkerAtCenter: false,
            rectangleSelector: {

            },
            coordinatesDisplay: true,
            mapChangeFont:true,
            additionalLayers: [buildStationsLayer()],
            interactions: [ selectStationInteraction],
            changeCursorHoverFeature : "pointer",
            onRectangleSelected : onRectangleSelected,


        };

    }

    var updateMapModelToMatchDateSelectionsTimer = setInterval(function(){
        if(updateMapModelToMatchDateSelections &&   $scope.searchState.mapModel){
            if (typeof $scope.updateIndex === "function") {
                console.log("update map model to match date selections");
                updateMapModelToMatchDateSelections= null;
                $scope.updateIndex();
                clearInterval(updateMapModelToMatchDateSelectionsTimer);
            }
        }
    },500);

}]);
