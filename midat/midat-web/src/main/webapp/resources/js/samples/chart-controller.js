samplesApp.controller('samplesChartCtrl',
    ["$scope", "$http","securityService","statisticsService","bioticIndexGradesService",
        function ($scope, $http,securityService,statisticsService,bioticIndexGradesService) {


            $scope.loadingStatUserUpload = true;
            $scope.loadingStatTempo = true;
            $scope.loadingStatIndice = true;

            $scope.bioticIndexQualityValues = bioticIndexGradesService.values;

            console.log($scope.bioticIndexQualityValues);
            // new V2.0
            moment.locale(JECI.lang);
            var previousServerRequestPromise=null;
            /*
              securityService.getSessionUser(function(sessionUser){
                console.log(sessionUser)
              })
            */

            $scope.series = [ 'PDM','PDL'];

            $scope.indexSeries = [ 'MIN','AVG','MAX'];


            $scope.colors= [ '#ff0000','#803690',  '#FDB45C', '#949FB1', '#4D5360']
            $scope.chartTypeValue='USERUPLOAD'

            $scope.logins=[];
            $scope.indexs=['IBCH','SPEAR','MAKROINDEX'];

            $scope.cantons=[];
            $scope.dataSetPerLogin=null;
            $scope.dataSetPerIndex=null;

            var checkUser ={
                'all':true
            }

            var checkIndex ={
                'IBCH':true,
                'SPEAR':true,
                'MAKROINDEX':true
            }


            var responseData =null;
            var labData=[];
            var massData=[];
            statisticsService.getSampleProtocolPerUserCanton(function(data){
                responseData = data;
                var pervCanton=null;
                var numberLabPerCanton=0;
                var numberMassPerCanton=0;
                for(var i=0; i< data.length;i++){
                    var currObj = data[i];
                    if( !isInArray(currObj.login, $scope.logins)){
                        $scope.logins.push(currObj.login)
                    }
                    if( !isInArray(currObj.canton, $scope.cantons)){
                        $scope.cantons.push(currObj.canton)
                    }

                    // this for the last element
                    if((i+1) === data.length){
                        numberLabPerCanton +=currObj.numberLab;
                        numberMassPerCanton +=currObj.numberMass;
                        labData.push(numberLabPerCanton)
                        massData.push(numberMassPerCanton)
                        numberLabPerCanton=0;
                        numberMassPerCanton=0;
                    }else if ((currObj.canton !== pervCanton && pervCanton !== null) ){
                        labData.push(numberLabPerCanton)
                        massData.push(numberMassPerCanton)
                        numberLabPerCanton=0;
                        numberMassPerCanton=0;
                    }
                    numberLabPerCanton +=currObj.numberLab;
                    numberMassPerCanton +=currObj.numberMass;
                    pervCanton =currObj.canton;

                }
                console.log( $scope.logins)
                console.log( $scope.cantons)
                console.log(labData)
                console.log(massData)
                var dataSetPerLogin={};
                dataSetPerLogin.all ={};
                dataSetPerLogin.all.pdm =massData;
                dataSetPerLogin.all.pdl =labData;
                for(var c= 0; c< $scope.cantons.length;c++){
                    for(var l= 0; l<  $scope.logins.length;l++){
                        if( !dataSetPerLogin[$scope.logins[l]]){
                            dataSetPerLogin[$scope.logins[l]] ={};
                            dataSetPerLogin[$scope.logins[l]].pdl =[];
                            dataSetPerLogin[$scope.logins[l]].pdm=[];
                        }
                        dataSetPerLogin[$scope.logins[l]].pdl.push(calculateNumberPerUserCantonTpye("numberLab",$scope.logins[l],$scope.cantons[c],responseData))
                        dataSetPerLogin[$scope.logins[l]].pdm.push(calculateNumberPerUserCantonTpye("numberMass",$scope.logins[l],$scope.cantons[c],responseData))

                    }
                }

                // $scope.dataSetPerLogin= dataSetPerLogin;
                var entries = Object.entries(dataSetPerLogin)
                $scope.dataSetPerLogin= entries;
                var myValues = Object.values(dataSetPerLogin)
                for (var x=0 ;x<myValues.length; x++) {
                    myValues[x].max_pdm=sumArray(myValues[x].pdm);
                    myValues[x].max_pdl=sumArray(myValues[x].pdl);
                    myValues[x].max= myValues[x].max_pdm +myValues[x].max_pdl;
                }
                console.log(entries)
                //console.log(dataSetPerLogin);

                $scope.loadingStatUserUpload = false;


            })

            $scope.checkUser = function(user){
                if(checkUser[user] && checkUser[user] === true){
                    return true
                }else{
                    return false
                }

            }






            $scope.toggleUser = function(user, isChecked){
                checkUser[user]=isChecked;
                console.log(checkUser)

            }


            function calculateNumberPerIndexCanton(sampleIndex, canton, dataSet){
                var number=[];
                for(var m=0; m< dataSet.length;m++){
                    var currentElem = dataSet[m]
                    if(currentElem.canton === canton ){

                        if("IBCH" === sampleIndex){
                            number.push(currentElem.minIBCH )
                            number.push(currentElem.avgIBCH)
                            number.push(currentElem.maxIBCH)
                        }else if("SPEAR" === sampleIndex){
                            number.push(currentElem.minSPEAR)
                            number.push(currentElem.avgSPEAR)
                            number.push(currentElem.maxSPEAR)
                        }else{
                            number.push(currentElem.minMAKROINDEX)
                            number.push(currentElem.avgMAKROINDEX)
                            number.push(currentElem.maxMAKROINDEX)
                        }
                        break;
                    }
                }
                console.log(sampleIndex)
                console.log(number)
                return number
            }

            function calculateNumberPerUserCantonTpye(portocolType,loginUser, canton, dataSet){

                var loginUserDataSet =[];
                for(var j=0; j< dataSet.length;j++){
                    var currentElem = dataSet[j]
                    if(currentElem.login ===loginUser ){
                        loginUserDataSet.push(currentElem)
                    }
                }

                var number=0;
                for(var m=0; m< loginUserDataSet.length;m++){
                    var currentElem = loginUserDataSet[m]
                    if(currentElem.canton === canton ){
                        if("numberLab" === portocolType){
                            number= currentElem.numberLab
                        }else{
                            number= currentElem.numberMass
                        }
                        return number;
                        break;
                    }
                }
                return number
            }


            statisticsService.getSampleProtocolPerMonth(function(data){
                console.log(data);
                var lineLabels=[];

                var pdlData=[];
                var pdmData=[];

                for(var i=0; i< data.length;i++){
                    var currObj = data[i];
                    lineLabels.push(currObj.month+'/'+currObj.year);
                    pdlData.push(currObj.numberLab)
                    pdmData.push(currObj.numberMass)
                }
                $scope.timeLabels=   lineLabels;
                $scope.lineData=   [pdmData,pdlData];

                $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
                $scope.options = {
                    scales: {
                        yAxes: [
                            {
                                id: 'y-axis-1',
                                type: 'linear',
                                display: true,
                                position: 'left'
                            },
                            {
                                id: 'y-axis-2',
                                type: 'linear',
                                display: true,
                                position: 'right'
                            }
                        ]
                    }
                };
                console.log( $scope.lineData)
                $scope.loadingStatTempo = false;

            })

            statisticsService.getSampleIndexHistory(function(data){
                var dataSetPerIndex={};
                for(var c= 0; c< $scope.cantons.length;c++){
                    for(var l= 0; l<  $scope.indexs.length;l++){
                        if( !dataSetPerIndex[$scope.indexs[l]]){
                            dataSetPerIndex[$scope.indexs[l]] ={};
                            dataSetPerIndex[$scope.indexs[l]].min =[];
                            dataSetPerIndex[$scope.indexs[l]].avg=[];
                            dataSetPerIndex[$scope.indexs[l]].max=[];
                        }
                        var indexdata = calculateNumberPerIndexCanton($scope.indexs[l],$scope.cantons[c],data);
                        dataSetPerIndex[$scope.indexs[l]].min.push(indexdata[0])
                        dataSetPerIndex[$scope.indexs[l]].avg.push(indexdata[1])
                        dataSetPerIndex[$scope.indexs[l]].max.push(indexdata[2])
                    }
                }

                var entriesIndex = Object.entries(dataSetPerIndex)
                $scope.dataSetPerIndex= entriesIndex;
                console.log(entriesIndex)

                $scope.loadingStatIndice = false;

            });

            $scope.checkIndex = function(myIndex){
                if(checkIndex[myIndex] && checkIndex[myIndex] === true){
                    return true
                }else{
                    return false
                }

            }

            $scope.toggleIndex = function(myIndex, isChecked){
                checkIndex[myIndex]=isChecked;
                console.log(checkIndex)

            }

            $scope.onClick = function (points, evt) {
                console.log(points, evt);
            };

            $scope.resetCheckUser= function(){
                checkUser ={
                    'all':true
                }
            }

            $scope.resetCheckIndex= function(){
                checkIndex ={
                    'all_index':true
                }
            }

            function isInArray(value, array) {
                return array.indexOf(value) > -1;
            }
            function sumArray(numbers) {
                var sum =0
                for (var i = 0; i < numbers.length; i++) {
                    sum += numbers[i]
                }
                return sum;
            }
        }]);
