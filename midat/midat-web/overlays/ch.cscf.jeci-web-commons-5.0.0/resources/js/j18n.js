$j18n = (function(){

    if(typeof $j18n !== 'undefined'){
        alert("Namespace collision : the global variable named \"$i18n\" is already defined.");
        return undefined;
    }

    //function that will be used by javascript to access localized messages
    var stuff = function(key){
        if($j18n.i18nCurrentPageMessages[key] || ($j18n.i18nCurrentPageMessages[key] != undefined && $j18n.i18nCurrentPageMessages[key].length === 0)){
            return $j18n.i18nCurrentPageMessages[key];
        }
        return "?!?"+key+"?!?";  //use an exclamation mark to differentiate if the message is missing from the js part or from the server part (???)
    };

    stuff.i18nCurrentPageMessages = {};

    return stuff;
})();



