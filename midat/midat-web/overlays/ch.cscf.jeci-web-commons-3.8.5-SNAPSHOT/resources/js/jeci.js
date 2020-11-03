STARTUP = {

    //function that receives a function name and fires it if it is present in one of our namespaces
    fire : function( funk ){
        if(JECI[funk]){
            JECI[funk]();
        }

        if(typeof MIDAT !== 'undefined'){
            if(MIDAT[funk]){
                MIDAT[funk]();
            }
        }

        if(typeof MDS !== 'undefined'){
            if(MDS[funk]){
                MDS[funk]();
            }
        }

        if(typeof IFF !== 'undefined'){
            if(IFF[funk]){
                IFF[funk]();
            }
        }
    },

    //general setup function
    //Fires the common function, then each function designated by the class names that are put on the body tag
    setupPage:function(  ) {

        STARTUP.fire("common");

        $.each(document.body.className.split(/\s+/),function(i, classname){
            STARTUP.fire(classname);
        });

        var bodyId = document.body.id;
        STARTUP.fire(bodyId);
    }
};

JECI = {

    appRoot: "placeholder", //value set in layout.jsp

    //Common function that is always executed for each page
    common: function(){

        // usage: log('inside coolFunc',this,arguments);
        // http://paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
        window.log = function(){
            log.history = log.history || [];   // store logs to an array for reference
            log.history.push(arguments);
            if(this.console){
                console.log( Array.prototype.slice.call(arguments) );
            }
        };

        //adds a random parameter to all ajax calls to force disble the cache
        //must be overridden for cases where caching is desirable
        $.ajaxSetup({ cache: false });

        //register the mustache templates
        JECI.registerMustacheTemplates();

        //override some of noty's defaults
        $.noty.defaults.layout = "topCenter";
        $.noty.defaults.closeWith = ["click", "button"];
        $.noty.defaults.timeout = 20000

        //override some of jqGrid defaults
        $.extend($.jgrid.defaults, {
            datatype: 'json',
            jsonReader : {
                repeatitems:false,
                total: function(result) {
                    //Total number of pages
                    return Math.ceil(result.total / result.max);
                },
                records: function(result) {
                    //Total number of records
                    return result.total;
                }
            },
            //request parameters name
            prmNames: {rows: 'pageSize', order: "sortOrder", sort : "orderBy"},
            height: 'auto',
            autowidth: true,
            viewrecords: true,
            rowList: [5, 10, 15, 20, 50, 100],
            altRows: false,
            loadError: function(xhr, status, error) {
                alert(error);
            },
            hoverrows: true,
            beforeSelectRow: function(rowid, e) {
                return false;
            }
        });

        // Override jQuery Ajax error handler
        var statusErrorMap = {
            '400' : "Bad Request",
            '401' : "Unauthorised",
            '403' : "Forbidden",
            '500' : "Internal Server Error",
            '503' : "Service Unavailable"
        };

         $(document).ajaxError(function(event, jqxhr, settings, exception){

             //special case : 401 Unauthorized, probably session expired, redirect to login page
             if(jqxhr.status == 401){
                 alert("A server request was made to an unauthorized source. Most probably, your session has expired.\n\nYou will now be redirected to the login page.");
                 window.location=JECI.appRoot+"/authentication/login";
                 return;
             }

             //try to get error object in JSON from response body
             var errorInfo;
             try{
                 errorInfo = jQuery.parseJSON( jqxhr.responseText );
             }catch(e){
                 //if no JSON body just show generic message
                 var message = "A request to the server has generated an unexpected error."
                 + "\nStatus : "+jqxhr.status + " " + statusErrorMap[jqxhr.status];
                 alert(message);
                 return;
             }

             var simpleMessage = "A request to the server generated an error : \n\n"+errorInfo.message+"\n\nShow error details ?";
             if(confirm(simpleMessage)){
                 var detailedMessage = "Error code : "+jqxhr.status
                     +"\nError type : "+errorInfo.type
                     +"\n\nMessage : "+errorInfo.message
                     +"\n\n Trace :\n";

                 var trace = errorInfo.cause.stackTrace;
                 for(var i=0; i<10 || i<trace.length; i++){
                     var call = trace[i];
                     detailedMessage += "\n" + call.className +"." + call.methodName + " in file "+ call.fileName +" line " + call.lineNumber;
                 }

                 if(trace.length > 10){
                     detailedMessage +="\n.....\n\n " + (trace.length-10) + " calls omitted."
                 }

                 alert(detailedMessage);
             }

         });

        JECI.handleNotifications();

    },

    form: function(){
        $("input.date").datepicker();
    },

    list: function(){
        $('a.deleteLink').click(function(){
            return confirm($(this).attr("data-confirm"));
        });
    },

    handleNotifications: function(ajaxHtmlBody){

        var rootElmt;
        var errors = 0;

        if(ajaxHtmlBody){
            rootElmt = ajaxHtmlBody.find(".jeciMessages");
        }else{
            rootElmt = $(".jeciMessages");
        }

        rootElmt.find(".success ul li").each(function(index, messageElmt){
            noty({
                text: $(messageElmt).html().trim(),
                type: "success"
            });
        });

        rootElmt.find(".error ul li").each(function(index, messageElmt){
            errors++;
            noty({
                text: $(messageElmt).html().trim(),
                type: "error"
            });
        });

        rootElmt.find(".notice ul li").each(function(index, messageElmt){
            noty({
                text: $(messageElmt).html().trim(),
                type: "warning"
            });
        });

        rootElmt.find(".info ul li").each(function(index, messageElmt){
            noty({
                text: $(messageElmt).html().trim(),
                type: "alert"
            });
        });

        return errors;
    },

    blockGui: function (loading){

        if(!JECI.overlayStack){
            JECI.overlayStack = new Array();
        }

        var overlay;

        if(loading){
            overlay = $('<div class="ui-widget-overlay ui-front"><i class="fa fa-cog fa-spin"></i></div>')
                .appendTo($("body"));
            overlay.find("loadingAnim").css("z-index", overlay.css("z-index")+1);
        }else{
            overlay = $('<div class="ui-widget-overlay ui-front"></div>')
                .appendTo($("body"));
        }

        JECI.overlayStack.push(overlay);

    },

    unblockGui: function (){

        if(JECI.overlayStack){
            var topOverlay = JECI.overlayStack.pop();
            if(topOverlay){
                topOverlay.remove();
            }
        }

    },

    serializeCheckedTreeNodes : function(tree){
        var checkedNodes = tree.jstree("get_checked", null, true);
        var selectedIdsStr = "";

        $.each(checkedNodes, function(i, checkedNode){
            if(tree.jstree("is_leaf", checkedNode)){
                var id = $(checkedNode).data("node-id");
                selectedIdsStr+=id;
                if(i<checkedNodes.length-1){
                    selectedIdsStr+=",";
                }
            }
        });
        return selectedIdsStr;
    },

    registerMustacheTemplates: function(){
        $.Mustache.add('edit-link-table', "<a class='edit btn btn-default btn-xs' href='{{url}}' title='{{tooltip}}'><i class='fa fa-edit'></i></a>");
        $.Mustache.add('delete-link-table', "<a class='delete btn btn-danger btn-xs' href='{{url}}' title='{{tooltip}}'><i class='fa fa-trash-o'></span></a>");
        $.Mustache.add('audit-link-table', "<a class='audit btn btn-default btn-xs' href='{{url}}' title='{{tooltip}}'><i class='fa fa-list'></a>");

        $.Mustache.add('audit-dialog', "<div class='auditInfoDialog'></div>");

        $.Mustache.load(JECI.appRoot+"/resources/templates/bs-modal.mst");
    },

    formatBooleanForGrid: function(cellvalue, options, rowObject){
        if(cellvalue){
            return "<span class='icon yesIcon'></span>";
        }else{
            return "";
        }
    },


    // Post to the provided URL with the specified parameters.
    // NB : this changes the page URL, this is NOT AJAX !
    // parameters is a JS object / associative array
    // method id optional and defaults to POST
    // if not equal to get or post, it will be added to the post request as a _method parameters as used by spring's HiddenHttpMethodFilter
    postAndGo : function (path, parameters, method) {
        var form = $('<form></form>');

        var _method;
        var specialMethod=false;

        if(method){
            if (!(method.toLowerCase() === "get" || method.toLowerCase() === "post")){
                specialMethod = true;
            }
        }else{
            _method = "post";
        }

        if(specialMethod){
            form.attr("method", "post");
            parameters["_method"] = _method;
        }else{
            form.attr("method", _method);
        }


        form.attr("action", path);

        $.each(parameters, function(key, value) {
            var field = $('<input />');

            field.attr("type", "hidden");
            field.attr("name", key);
            field.attr("value", value);

            form.append(field);
        });

        // The form needs to be a part of the document in
        // sortOrder for us to be able to submit it.
        $(document.body).append(form);
        form.submit();
    },

    goTo : function(url, replace) {
        if(replace){
            window.location.replace(url);
        }else{
            window.location = url;
        }
    }
} ;

$(function(){
    STARTUP.setupPage();
});
