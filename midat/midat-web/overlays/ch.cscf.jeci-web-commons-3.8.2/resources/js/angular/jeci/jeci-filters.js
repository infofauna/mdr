angular.module("jeciFilters", [])
    
.filter("multilangValue", function() {
    return function(input) {
        var val = input[JECI.i18nlang];
        if(val != null){ return val; }

        val = input["fr"];
        if(val != null){ return val; }

        val = input["de"];
        if(val != null){ return val; }

        val = input["it"];
        if(val != null){ return val; }

        val = input["en"];
        if(val != null){ return val; }

        val = input["la"];
        if(val != null){ return val; }

        return "[No value in any known language]";
    };
})
    
.filter("bioticIndex", function(){
    return function(input){
        var out = "";
        for(var propt in input){
            out += propt + "=" + input[propt] + " ";
        }
        return out;
    }
})

.filter("j18n", function() {
    return function (key) {
        return $j18n(key);
    }
})

.filter("stringArray", function() {
    return function(array){
        return array.join(", ");
    }
});