      MIDAT = {

                    /**
                     * Function that sets up the dynamic features on the lab protocol import page:
                     * - Ajax upload and populate of the excel sheets select
                     * - Date pickers (jquery ui)
                     * - Double select for the precision reference
                     */
                    labProtocolForm: function(){

                        if (window.midatFlags !== undefined){
                            if(midatFlags.isWaitingForMassTask){
                                var markup = $("#import-in-progress-modal").html();
                                var dialog = $(markup);
                                dialog.appendTo($("body"));
                                dialog.modal({backdrop:"static"});

                                var taskKey=midatFlags.massTaskKey;
                                log("Now watching task with key=", taskKey);

                                function poll(){

                                    $.get(JECI.appRoot + '/api/task-status/'+taskKey, function(data) {
                                        if(data.done){
                                            var url = JECI.appRoot + "/app/import/mass/task-result/"+taskKey ;
                                            JECI.goTo(url, true);
                                        }else{
                                            setTimeout(poll,3000);
                                            dialog.find(".progress-bar")
                                                .css("width", data.percentageProcessed+"%")
                                                .html(data.percentageProcessed+"%");

                                            dialog.find("#current-process-state").html(data.currentStep);
                                        }
                                    });
                                }
                                poll();
                            }
                        }

                        //submit button
                        var submitButton = $("#submitButton").prop("disabled",true);

                        //upload input
                        var uploadInput = $("#excelFileInput");

                        //file upload
                        var uploadParent = uploadInput.closest("div");

                        //upload button
                        var uploadButton = uploadParent.find(".file-input-wrapper");

                        //loading icon
                        var uploadSpinner = uploadParent.find("i.fa").hide();


                        //file upload
                        uploadInput.fileupload({
                            dataType: 'json',
                            url: JECI.appRoot+"/api/upload/protocol-excel-file",

                            done: function (e, data) {
                                $.each(data.result, function (index, file) {

                                    uploadParent.find("label span.fileName").html(file.fileName);

                                    // upload the excel file sheet select
                                    var sheetSelect = $("select[name='excelSheetName']");
                                    sheetSelect.find("option").remove();

                                    $.each(file.handlerResult, function(index, sheetName){
                                        $("<option value='"+sheetName+"'>"+sheetName+"</option> ")
                                            .appendTo(sheetSelect);
                                    });

                                    // remove loading and re-enbale the control
                                    sheetSelect.prop("disabled", false);
                                    submitButton.prop("disabled", false);
                                    uploadInput.prop("disabled", false);
                                    uploadButton.removeClass("disabled");
                                    uploadSpinner.hide();
                                });
                            }
                        }).bind("fileuploadadd", function(){
                            //disable while loading
                            uploadInput.prop("disabled", true);
                            uploadButton.addClass("disabled");
                            uploadSpinner.show();

                        });

                        var refSysSelect=$("select[name='referenceSystemId']");
                        var precisionSelect=$("select[name='precisionLevelId']");

                        updatePrecisionSelect();

                        refSysSelect.change(updatePrecisionSelect);

                        function updatePrecisionSelect(){
                            precisionSelect.find("option").remove();

                            var refSysId = refSysSelect.val();

                            $.getJSON(JECI.appRoot+"/api/options/references/"+refSysId+"/precision-levels", function(options){
                                $.each(options, function(index, option){
                                    $("<option value='"+option.value+"'>"+option.label+"</option> ")
                                        .appendTo(precisionSelect);
                                });
                            });
                        }


                    },
                    /**
                     * Function that sets up the dynamis features on the mass data import page.
                     * Based on the lab import page since it is almost identical.
                     */
                    massImportForm: function(){
                        MIDAT.labProtocolForm();
                    },

                    protocolValidationResult: function(){

                        $("#show-only-warnings-errors-chk").change(function() {
                            if($(this).is(":checked")) {
                                //'checked' event code
                                $("#messages-table tr.info").hide();
                                return;
                            }
                            //'unchecked' event code
                            $("#messages-table tr.info").show();

                        });

                        //disable buttons to avoid double-post
                        $("#confirmImportForm").on("submit", function(){

                            $("#confirm-import-button").prop("disabled",true);

                            $("#cancel-button")
                                .click(function(e) {
                                    e.preventDefault();
                                })
                                .addClass("disabled");

                        });
                    },

                    additionalDataForm : function(){

                        //version select depends on selected protocol type
                        var refSysSelect=$("select[name='referenceSystemId']");

                        var versionSelect=$("select[name='protocolVersionId']");

                        //update version select when protocol type is changed
                        $("input[name='protocolTypeRadio'][type='radio']").change(updateVersionSelect);
                        $("input[name='protocolTypeRadio'][type='radio']:first").attr("checked", true);
                        updateVersionSelect();

                        //submit button
                        var submitButton = $("#submitButton").prop("disabled",true);

                        //upload input
                        var uploadInput = $("#excelFileInput");

                        //file upload
                        var uploadParent = uploadInput.closest("div");

                        //upload button
                        var uploadButton = uploadParent.find(".file-input-wrapper");

                        //loading icon
                        var uploadSpinner = uploadParent.find("i.fa").hide();

                        var upload = $('#excelFileInput').fileupload({
                            dataType: 'json',
                            url: JECI.appRoot+"/api/upload/protocol-excel-file",
                            done: function (e, data) {
                                $.each(data.result, function (index, file) {

                                    uploadParent.find("label span.fileName").html(file.fileName);

                                    var sheetSelect = $("select[name='excelSheetName']");
                                    sheetSelect.find("option").remove();

                                    $.each(file.handlerResult, function(index, sheetName){
                                        $("<option value='"+sheetName+"'>"+sheetName+"</option> ")
                                            .appendTo(sheetSelect);
                                    });

                                    sheetSelect.prop("disabled", false);
                                    submitButton.prop("disabled",false);
                                    uploadSpinner.hide();
                                    uploadInput.prop("disabled", true);
                                    uploadButton.removeClass("disabled");
                                });
                            }
                        }).bind("fileuploadadd", function(){
                            $("#excelFileInput").prop("disabled", true);
                            uploadButton.addClass("disabled");
                            uploadSpinner.show();

                        });;

                        //delete buttons
                        //delete lab protocol (main entry)
                        $(".deleteLabProtocolButton").click(function(){
                            if(confirm($j18n("import.protocol.additional.importedProtocols.delete.labProtocol.confirm"))){
                                JECI.postAndGo($(this).attr("href"), {}, "delete")
                            }
                            return false;
                        });

                        //delete evaluation grid
                        $(".deleteEvaluationGridButton").click(function(){
                            if(confirm($j18n("import.protocol.additional.importedProtocols.delete.evaluationGrid.confirm"))){
                                JECI.postAndGo($(this).attr("href"), {}, "delete")
                            }
                            return false;
                        });

                        //delete field protocol
                        $(".deleteFieldProtocolButton").click(function(){
                            if(confirm($j18n("import.protocol.additional.importedProtocols.delete.fieldProtocol.confirm"))){
                                JECI.postAndGo($(this).attr("href"), {}, "delete")
                            }
                            return false;
                        });

                        function updateVersionSelect(){
                            versionSelect.find("option").remove();

                            var checkedRadio = $("input[name='protocolTypeRadio'][type='radio']:checked");

                            if(checkedRadio.length == 0){
                                return;
                            }

                            var protocolTypeId = checkedRadio.val();

                            var url = JECI.appRoot+"/api/options/protocol-type/"+protocolTypeId+"/protocol-versions";

                            $.getJSON(url, function(options){
                                $.each(options, function(index, option){
                                    $("<option value='"+option.value+"'>"+option.label+"</option> ")
                                        .appendTo(versionSelect);
                                });
                            });
        }

    }

};