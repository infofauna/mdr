
JECI.dialogForm = {};


/**
 * Sets up dialog form for a table representing entities.
 * It will inspect each row for a link (<a> tag) of class "edit" and set up a dialog form for this link's click event.
 * The after save callback includes the reloading of the grid.
 * @param grid
 * @param customSetupCallback
 * @param afterSaveCallback
 * @param options :
 */
JECI.dialogForm.setUpTableEditLinks = function(grid, title, customSetupCallback, afterSaveCallback){


    if(!grid){
        throw "JECI.dialogform() : the parameter 'grid' is mandatory";
    }

    grid.find("a.edit").each(function(index, a){
        var link = $(a).click(function(){
            JECI.dialogForm.open(link, title, customSetupCallback, function(){
                grid.trigger("reloadGrid");
                if(afterSaveCallback){
                    afterSaveCallback();
                }
            });
            return false;
        });
    });
};

JECI.dialogForm.setupSingleLink = function(a, title, customSetupCallback, afterSaveCallback){
    var link = $(a).click(function(){
        JECI.dialogForm.open(link, title, customSetupCallback, afterSaveCallback);
        return false;
    });
};

JECI.dialogForm.open = function(link, title, customSetupCallback, afterSaveCallback){

    /*dialog.dialog({
     height: 700,
     width: 750,
     modal: true,
     resizable: false,
     closeOnEscape: false,
     title: "loading...",
     close: function(){
     dialog.remove();
     },
     buttons : [
     {text : $j18n("global.actions.save"), click: function(){
     dialog.find("form").submit();
     }},
     {text : $j18n("global.actions.cancel"), click: function(){
     dialog.dialog("close");
     }}
     ]
     });*/

    var dialog = $($.Mustache.render("bs-modal",
        {
            title : "Loading...",
            dialogClasses : "modal-lg",
            footer : {
                buttons: [
                    {
                        class: "cancel btn-default",
                        icon: "fa-times",
                        label: $j18n("global.actions.cancel")
                    },
                    {
                        class: "save btn-primary",
                        icon: "fa-check",
                        label: $j18n("global.actions.save")
                    }
                ]
            }
        }
    )).appendTo($("body"));

    dialog.modal({
        closeExisting: true
    });

    dialog.find("button.save").click(function(){
        dialog.find("form").submit();
        return false;
    });

    dialog.find("button.cancel").click(function(){
        close();
        return false;
    });

    dialog.find("button.close").click(function(){
        close();
        return false;
    });

    function close(){
        console.log("closing dialog .........");
        dialog.modal("hide");
        dialog.on("hidden.bs.modal", function(){
            dialog.remove();
        })
    }

    JECI.blockGui(true);

    dialog.find(".modal-body").load(link.attr("href"), function(){

        //dialog.dialog("option", "title", title);
        dialog.find("h4").html(title);

        var setupForm = function(){

            dialog.find("form").submit(function(){

                var form = $(this);

                var url = form.attr("action");
                var formData = form.serialize();

                //block form with overlay and show "loading" animation
                //JECI.blockGui(true);

                $.ajax(url, {
                    type: "POST",
                    data: formData,
                    success : function(data, textStatus){

                        var result = $($.trim(data));

                        JECI.handleNotifications($(data));

                        //if form found in the result value : the form was returned with validation errors
                        if (result.is("form") || result.find("form").length){
                            dialog.find(".modal-body").html(result);
                            setupForm();
                        }
                        //else reload the grid
                        else{
                            if(afterSaveCallback){
                                afterSaveCallback();
                            }
                            close();
                        }
                        JECI.unblockGui();
                    },
                    error : function(){

                    }
                });
                return false;
            });
        };
        setupForm();

        if(customSetupCallback){
            customSetupCallback(dialog);
        }

        JECI.unblockGui();
    });
};

