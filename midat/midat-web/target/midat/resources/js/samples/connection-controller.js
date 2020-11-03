samplesApp.controller('userConnectionCtrl',
    ["$scope", "$http","securityService","statisticsService",
        function ($scope, $http,securityService,statisticsService) {


            // new V2.0
            moment.locale(JECI.lang);

            var orignalData = null;
            $scope.filterTable = function( obj){
                var filtered  = orignalData.filter(function(row){

                    var login = row.login ? row.login :'';
                    var lastname = row.lastname ? row.lastname :'';
                    var firstname = row.firstname ? row.firstname :'';

                    if(isInString(obj,login) ||isInString(obj,lastname) || isInString(obj,firstname)){
                        return true
                    }
                    return false
                })
                console.log(filtered)
                loadGrid(filtered)


            }

            statisticsService.getUsersConnectionHistory(function(data){
                orignalData = data;
                loadGrid(data);
                $("#listConnection").jsGrid("sort", { field: "lastconnection", order: "desc" });

            })

            loadGrid = function(data){
                $("#listConnection").jsGrid({
                    width: "100%",
                    height: "543px",
                    filtering: false,
                    sorting: true,
                    paging: true,
                    pageSize: 10,
                    pageButtonCount: 10,
                    data: data,
                    pagerFormat: "Pages: {first} {prev} {pages} {next} {last}    {pageIndex} / {pageCount}",
                    pagePrevText: "Précédent",
                    pageNextText: "Suivant",
                    pageFirstText: "Première page",
                    pageLastText: "Dernière page",

                    pageNavigatorNextText: "...",
                    pageNavigatorPrevText: "...",

                    fields: [

                        {   title:"Login",
                            name: "login",
                            sorter: function(val1, val2) {
                                return val1.localeCompare(val2);
                            },
                            type: "text", width: 20, sorting:true
                        },
                        {   title:"Ldap",
                            name: "ldap",
                            sorter: function(val1, val2) {
                                if (val1 ===true){
                                    return 1
                                } else if(val2 ===true){
                                    return -1
                                }
                                return 0
                            },
                            type: "checkbox", width: 20, sorting:true
                        },
                        {   title:"Lastname",
                            name: "lastname",
                            sorter: function(val1, val2) {
                                if(!val1) val1 ='';
                                if(!val2) val2='';
                                return val1.localeCompare(val2);
                            },
                            type: "text", width: 25, sorting:true
                        },
                        {   title:"Firstname",
                            name: "firstname",
                            sorter: function(val1, val2) {
                                if(!val1) val1 ='';
                                if(!val2) val2='';
                                return val1.localeCompare(val2);
                            },
                            type: "text", width: 25, sorting:true
                        },
                        {   title:"Adresse",
                            name: "address",
                            sorter: function(val1, val2) {
                                if(!val1) val1 ='';
                                if(!val2) val2='';
                                return val1.localeCompare(val2);
                            },
                            type: "text", width: 35, sorting:true
                        },
                        {   title:"Localité",
                            name: "locality",
                            sorter: function(val1, val2) {
                                if(!val1) val1 ='';
                                if(!val2) val2='';
                                return val1.localeCompare(val2);
                            },
                            type: "text", width: 35, sorting:true
                        },

                        {
                            title:"Date de création",
                            name: "creationDate",
                            sorter: function(val1, val2) {
                                var date1 = moment(val1, "DD/MM/YYYY HH:mm:SS");
                                var date2 = moment(val2, "DD/MM/YYYY HH:mm:SS");
                                return date1 - date2 ;
                            },
                            type: "text", width: 30 ,sorting:true
                        },
                        {
                            title:"Première conn",
                            name: "firstconnection",
                            sorter: function(val1, val2) {
                                var date1 = moment(val1, "DD/MM/YYYY HH:mm:SS");
                                var date2 = moment(val2, "DD/MM/YYYY HH:mm:SS");
                                return date1 - date2 ;
                            },
                            type: "text", width: 30 ,sorting:true
                        },
                        {
                            title:"Dernière conn",
                            name: "lastconnection",
                            sorter: function(val1, val2) {
                                var date1 = moment(val1, "DD/MM/YYYY HH:mm:SS");
                                var date2 = moment(val2, "DD/MM/YYYY HH:mm:SS");
                                return date1 - date2 ;
                            },
                            type: "text", width: 30 ,sorting:true
                        },
                        {   title:"# conn",
                            name: "connectioncount",
                            sorter: function(val1, val2) {
                                if(val1 - val2 >0){
                                    return 1
                                }else if(val2-val1>0){
                                    return -1
                                }
                                return 0;
                            },
                            type: "number", width: 10 ,sorting:true
                        }

                    ]
                });

                $("#listConnection").jsGrid("refresh");
            }



            function isInString(value, theString) {
                return theString.toLowerCase().indexOf(value.toLowerCase()) > -1;
            }




        }]);
