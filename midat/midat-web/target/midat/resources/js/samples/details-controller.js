samplesApp.controller('sampleDetailsCtrl', ["$scope", "$http", "$routeParams", "$timeout", "config", "bioticIndexGradesService", "samplesService" ,"securityService", function ($scope, $http, $routeParams, $timeout, config, bioticIndexGradesService, samplesService,securityService) {

    $scope.loading=true;
    $scope.showLogTab = false;

    var sampleId = $routeParams.sampleId;

    log(" deatils-controller.js - Loading details for sample with sample ID :" + sampleId);
    var sampleDetailsRestUrl= config.samplesUrl + sampleId;
    var sampleimportlogUrl = config.sampleimportlogUrl+ sampleId;
    var sampleInfoIbchDataUrl = config.sampleInfoIbchDataUrl+sampleId;

    var sampleIndiceHistoryDataUrl = config.sampleIndiceHistoryDataUrl+sampleId;

    $scope.groundProtocolItemTemplatePath=config.groundProtocolItemTplUrl;

    $scope.indexTabTemplatePath = config.indexTabTemplatePathUrl;
    $scope.indexHistoryTabTemplatePath = config.indexHistoryTemplatePathUrl;



    $scope.indexesPopoverTplUrl=config.indexRatingPopoverTplUrl;
    $scope.bioticIndexQualityValues = bioticIndexGradesService.values;

    $scope.mapModel={};

    $scope.setMap=function(map){

        $scope.map=map;
    };

    $scope.back = function(){
        window.history.back();
    };

    $scope.forceRenderMap=function(){
        if($scope.mapModel.map){
            $scope.mapModel.map.updateSize();
        }
    };

    $scope.hasDocuments=function(){
        return $scope.sample && $scope.sample.documents && Object.keys($scope.sample.documents).length > 0;
    };



    $http.get(sampleDetailsRestUrl).success(function(data){
        $scope.sample=data;
        $scope.additionalDataFormUrl = config.additionalDataFormUrl.replace("{sampleId}", $scope.sample.sampleId);
        $scope.loading=false;

        /*
         workaround to display the pin on the map when show the details of a sample
         */
        $scope.mapModel={
            height: "300px",
            showMarkerAtCenter: true,
            coordinatesDisplay: true,
            mapChangeFont:false,
            resolution:5,
            hide: false
        }
        $scope.mapModel.center=[$scope.sample.stationCoordinatesX, $scope.sample.stationCoordinatesY];

        /* Add SampleStationStations */
        $scope.originalStations = $scope.sample.sampleStationStations;
        $scope.sampleStationStations =$scope.sample.sampleStationStations;
        $scope.stationStations =$scope.sample.stationStations;

        $scope.taxonIndicator= getTaxonIndicator($scope.sample.sampleTaxonIndicateurs);

        $scope.mainStationId =$scope.sample.stationId;


    });

    $scope.auditPopoverTplUrl = config.auditPopoverTplUrl;

    $scope.loadAuditingData = function(sampleId){
        samplesService.getAuditingInfo(sampleId, function(auditingInfo){
            $scope.auditingInfo = auditingInfo;
        });
    };

    $scope.countTaxons= function(labRecordFields,taxonName){
        if(labRecordFields ==null){
            return 0;
        }else{
            var labRecordFieldsDistinct = removeDumplicateValue(labRecordFields)
            var res= $.grep(labRecordFieldsDistinct,function(rec){
                return  rec.infofaunaTaxon.indexOf(taxonName) >= 0;
            })

            return res !=null ?res.length:0;
        }
    }

    $scope.countTaxonsForEphemeroptera= function(labRecordFields){
        console.log(" in countTaxonsForEphemeroptera...")
        var taxonName ='Ephemeroptera';
        if(labRecordFields ==null){
            return 0;
        }else{
            var labRecordFieldsDistinct = removeDumplicateValue(labRecordFields)
            var res= $.grep(labRecordFieldsDistinct,function(rec){
                return  rec.infofaunaTaxon.indexOf(taxonName) >= 0;
            })
            var taxonsTarget =[]
            console.log("countTaxonsForEphemeroptera",res)
            var subRec = $.each(res,function(index, elem){
                // elem is /Ephemeroptera/

                var _temp = elem.infofaunaTaxon.split('/')
                console.log("countTaxonsForEphemeroptera _temp",_temp)

                var taxons =[]
                $.each(_temp, function(_,el){
                    taxons.push(el.trim())
                })
                $.each(taxons, function(index2,el2){
                    if(el2 == taxonName){

                        taxonsTarget.push(taxons[index2+1])
                    }
                })
            })

            console.log("taxonsTarget",taxonsTarget)
            if(taxonsTarget.length>0){
                var res = unique(taxonsTarget)
                console.log("taxonsTarget unique ",res)
                return res !=null ?res.length:0;
            }
            return 0;
        }
    }

    function unique(array){
        return array.filter(function(el, index, arr) {
            return index === arr.indexOf(el);
        });
    }
    $scope.countNbrFamiliesOrGroups= function(labRecordFields){
        console.log('called countNbrFamiliesOrGroups')
        if(labRecordFields ==null){
            return 0;
        }else{
            var labRecordFieldsDistinct = removeDumplicateValue(labRecordFields)
            return labRecordFieldsDistinct !=null ?labRecordFieldsDistinct.length:0;
        }
    }
    /*
        {
          "ibchTaxon": null,
          "infofaunaTaxon": "Arthropoda / Insecta / Ephemeroptera / Ephemerellidae / Ephemerella",
          "stadium": null,
          "comment": null,
          "frequency": 2,
          "sortOrder": 2,
          "taxonId": 201997
        }
     */
    function removeDumplicateValue(myArray){
        var newArray = [];
        $.each(myArray, function(key, value) {
            var exists = false;
            $.each(newArray, function(k, val2) {
                if( value.taxonId == val2.taxonId){
                    console.log('found duplicate taxonId:', value.taxonId)
                    exists = true
                };
            });
            if(exists == false ) {
                newArray.push(value);
            }
        });

        return newArray;
    }
     $scope.sampleStationStations=[];
     $scope.stationStations=[];


     $scope.mainStationId = -1;



     $scope.stationInfoId=-1;
     $scope.showMoreInfo = function(id){
        $scope.stationInfoId = id;
     }

    $scope.switchHelper = function(value) {
        if (value < 700){
            return 300;
        }else{
            return 700;
        }
    };
     $scope.formatMsg= function(msg,d,m,y,altitude,calendarInterval){
          var date = d+'.'+m+'.'+y;
          var msg = $j18n(msg);
          var displayMsg = msg.replace("{0}",date).replace("{1}",altitude);

          if(calendarInterval){
              displayMsg = displayMsg.replace("{2}"," ( "+calendarInterval+" ) ");
          }else{
              displayMsg = displayMsg.replace("{2}"," ");
          }
          return displayMsg;
     }


      $scope.taxonIndicator ="";

      function getTaxonIndicator(taxons){
          var result ="";
         //console.log(JSON.stringify(taxons,null,3));
         function SortByFrequency(a, b){
             var aName = a.taxonIndicateurName;
             var bName = b.taxonIndicateurName;
             return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
         }

         if(taxons.length > 0){
             taxons.sort(SortByFrequency);
             $.each(taxons, function( index, value ) {
                 if(index < taxons.length -1){
                     result+= value.taxonIndicateurName +", ";
                 }else{
                     result+= value.taxonIndicateurName ;
                 }

             });
         }
          return result;
     }


      updateDb = function(){
          var queryString = "?mStation="+$scope.mainStationId+"&cStations=";
          var arrayStation = sampleStationStations;

          if(arrayStation){
              for ( var i = 0; i < arrayStation.length;i++ ) {
                  if(arrayStation.length -1 == i){
                      queryString +=  arrayStation[i].id;
                  }else{
                      queryString +=  arrayStation[i].id+",";
                  }
              }
          }
          console.log(" queryString:"+ queryString);
          $http.post(config.stationLinkunlinkUrl+queryString).then(function (data, status, headers, config) {
              console.log("success saving");
          },function (data, status, headers, config) {
              console.log("error saving");
          });

      }

    securityService.getSessionUser(function(sessionUser){
        $scope.sessionUser = sessionUser;

        if(sessionUser &&  sessionUser.permissions && sessionUser.permissions.length>0){
            managerObject = sessionUser.permissions.indexOf("midat:samples:update") > -1;
            if(managerObject ){
                $scope.canModifySampleStationStations = true;
                $scope.showLogTab= true;
            }else{
                $scope.canModifySampleStationStations = false;
                $scope.showLogTab= false;
            }
            console.log("canModifySampleStationStations is  :"+$scope.canModifySampleStationStations);
        }


    });

    $scope.showTooltip= function(){
        console.log($(this) + ' was clicked!');
        $(this).mouseenter();
    }

    $scope.infoChecked= true;
    $scope.warningChecked= true;

    $scope.loadImportLog = function(){
        $scope.loadingJournal=true;
        $http.get(sampleimportlogUrl).success(function(data){
            $scope.loadingJournal=false;
            $scope.importLog=data;
        })
    }

    $scope.loadSampleInfoIbchData = function(){

        $scope.loadingSampleInfoIbchData=true;
        $http.get(sampleInfoIbchDataUrl).success(function(data){
            $scope.loadingSampleInfoIbchData=false;
            $scope.sampleInfoIbchData=data;

        })
    }

    $scope.toggleInfo= function(){
        $scope.infoChecked = ! $scope.infoChecked;
    }

    $scope.toggleWarning= function(){
        $scope.warningChecked = ! $scope.warningChecked;
    }

    $scope.loadSampleIndiceHistoryData = function(){

        $scope.loadingSampleIndiceHistoryData=true;
        $http.get(sampleIndiceHistoryDataUrl).success(function(data){
            $scope.loadingSampleIndiceHistoryData=false;
            $scope.sampleIndiceHistoryData=data;

        })
    }


}]);
