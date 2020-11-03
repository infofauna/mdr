


JECI.grid = {

    /**
     * Options :
     *      crudUrl : the base URL for the CRUD controller. Optional. If not specified, the CRUD functionality is disabled.
     *      auditUrl : the base URL for the audit info controller. If not specified, the audit info functionality is disabled.
     *      crudFormSetupCallback : a function to be called after the crud dialog form is loaded, to customize the form (e.g. date pickers, double selects, etc.). Optional.
     *      afterSaveCallback : a function to be called after the CRUD form was successfully submitted and the entity saved. Optional.
     *      sort : the initial sort column, Optional. Default : empty string.
     *      sortOrder : the initial sort sortOrder "asc" or "des". Optional. Default : "asc"
     *      jqGridOptions : a jqGrid option object, allows to override additional jquGrid options for this table. Optional.
     *
     * Texts : an object containing the texts for the UI (optionnal) :
     *      editButton : title text for the edit button. Default : "Edit".
     *      deleteButton : title text for the delete button. Default : "Delete"
     *      createButton : title text for the create button. Default : "Create new"
     *      editFormTitle : title of the form dialog when editing an entity. Default : "Edition form"
     *      createFormTitle : title of the form dialog when adding a new entity. Default : "Creation form"
     *      confirmDeleteMessage : confirmation prompt after clicking the delete button. Default : "Are you sure you want to delete this ?"
     *
     * @param gridBaseElmt : the base element for the grid. Must be a table. Mandatory. Can be a jQuery object,
     * or a selector (id), or an element. Jquery element is recommended because it allows to keep a handle on the grid
     * since the same jQUery object will be used to create the grid.
     * @param jsonDataUrl : the base URL for the JSON data for the grid. Mandatory.
     * @param colModel : the jqGrid colmodel. Mandatory.
     */
    setup: function(gridBaseElmt, jsonDataUrl, colModel, texts, options){

        var grid = $(gridBaseElmt);

        var sortname = "";
        if(options.sort){
            sortname = options.sort;
        }

        var sortOrder = "asc";
        if(options.sordOrder && options.sordOrder === "desc"){
            sortOrder = "desc";
        }

        var defaultTexts = {
            editButton : "Edit",
            deleteButton : "Delete",
            createButton : "Create new",
            editFormTitle : "Edition form",
            createFormTitle : "Creation form",
            confirmDeleteMessage : "Are you sure you want to delete this ?"
        };

        function getText(key){
            return (texts && texts[key]) ? texts[key] : defaultTexts[key];
        }

        grid.jqGrid({
            url: jsonDataUrl,
            sortname: sortname,
            sortorder: sortOrder,
            colModel:colModel,
            pager : '#pager',

            gridComplete: function(){

                if(options.crudUrl){
                    //add the edit and delete links and set up the edit dialog form
                    var ids = grid.jqGrid('getDataIDs');
                    for(var i =0; i < ids.length; i++){
                        var id = ids[i];
                        var editLink = $.Mustache.render('edit-link-table', {url: options.crudUrl+"/"+id, tooltip : getText("editButton")});
                        var deleteLink = $.Mustache.render('delete-link-table', {url: options.crudUrl+"/"+id, tooltip : getText("deleteButton")});
                        grid.jqGrid("setRowData", id, {actions: editLink+deleteLink});
                    }

                    //dialog form for the edit buttons with 2 callbacks
                    // - for the form setup
                    // - for after an entity is saved
                    JECI.dialogForm.setUpTableEditLinks(grid, getText("editFormTitle"),
                        function(dialog){
                            if(options.crudFormSetupCallback){
                                options.crudFormSetupCallback(dialog);
                            }
                        },

                        function(){
                            if(options.afterSaveCallback){
                                options.afterSaveCallback();
                            }
                        }
                    );

                    //behavior for the delete buttons
                    grid.find("a.delete").click(function(){
                        if(confirm(getText("confirmDeleteMessage"))){
                            $.ajax($(this).attr("href"), {
                                type: "DELETE",
                                success : function(data, textStatus){
                                    var result = $($.trim(data));
                                    JECI.handleNotifications($(data));
                                    grid.trigger("reloadGrid");
                                },
                                error : function(){

                                }
                            });
                        }
                        return false;
                    });

                    //add the "navigator" with grid reload button and custom add button
                    grid.jqGrid('navGrid',"#pager", {add:false, edit:false, del:false, view:false, nav:false, search:false});

                    //remove previously created addbutton if any
                    $("#addButton").remove();

                    if(options.crudUrl){
                        grid.jqGrid('navButtonAdd',"#pager",{
                                id:"addButton",
                                caption:"",
                                title: getText("createButton"),   //does not work ?
                                buttonicon:"ui-icon-circle-plus",
                                onClickButton:null,
                                position: "first",
                                title:"",
                                cursor: "pointer"}
                        );
                    }

                    //behaviour of add button
                    var addButton = $("#addButton");
                    addButton.attr("title", getText("createButton"))
                        .attr("href", options.crudUrl+"/new");

                    //set up the form for add button
                    JECI.dialogForm.setupSingleLink(
                        addButton,
                        getText("createFormTitle"),
                        function(dialog){
                            if(options.crudFormSetupCallback){
                                options.crudFormSetupCallback(dialog);
                            }
                        },

                        function(){
                            grid.trigger("reloadGrid");
                            if(options.afterSaveCallback){
                                options.afterSaveCallback();
                            }
                        }
                    );

                }

                //audit info link column
                if(options.auditUrl){

                    var ids = grid.jqGrid('getDataIDs');
                    for(var i =0; i < ids.length; i++){
                        var id = ids[i];
                        var auditLink = $.Mustache.render('audit-link-table', {url: options.auditUrl+"/"+id, tooltip : getText("auditButton")});
                        grid.jqGrid("setRowData", id, {audit: auditLink});
                    }

                    grid.find("a.audit").click(function(){

                        var markup = $.Mustache.render("bs-modal",
                            {
                                title : "Loading...",
                            }
                        );

                        var dialog = $(markup).appendTo($("body")).modal({});

                        dialog.on("hidden.bs.modal", function(){
                            dialog.remove();
                        });


                        var dialogBody = dialog.find(".modal-body").load($(this).attr("href"), function(){
                            var title = dialogBody.find("h3").remove().html();
                            dialog.find("h4").html(title);
                        });

                        return false;
                    });
                }



            }
        });
    },

    i18nValueFormatter: function(cellvalue, options, rowObject){
        var val = cellvalue[JECI.i18nlang];
        if(val != null){ return val; }

        val = cellvalue['fr'];
        if(val != null){ return val; }

        val = cellvalue['de'];
        if(val != null){ return val; }

        val = cellvalue['it'];
        if(val != null){ return val; }

        val = cellvalue['en'];
        if(val != null){ return val; }

        val = cellvalue['la'];
        if(val != null){ return val; }

        return "[No value in any known language]";




    },

    //Parses dates sent as Unix timestamps in milliseconds, as received from Java, and format as "22.11.2013"
    dateFormatter: function (cellval) {
        var date = new Date(cellval);
        return $.datepicker.formatDate("dd.mm.yy", date);
    }




};