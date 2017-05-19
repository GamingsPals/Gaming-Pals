app.service("storage",function(){

    this.watchers = {};

    this.add = function(scope,name){
        let result = sessionStorage.getItem(name);
        this.addListener(name,scope);
        if (typeof result==="undefined" || result==="undefined" || !result || result==="null"){
            return {};
        }
        result = JSON.parse(result);
        result = this.parse(result);
        return result;
    };


    this.addListener = function(name,scope){
        let object = this;
        this.watchers[name] = scope.$watch(function (a) {
            if (typeof scope[name] !== "undefined") {
                sessionStorage.setItem(name, JSON.stringify(scope[name]));
            }
        });
    };



    this.parse = function(result){
        let res = {};
        if(typeof result ==="object") {
            for (let i in result) {
                res[i] = result[i];
                var patt = new RegExp(/(.*)-(.*)-(.*)(.*)T(.*)23(.*)/);
                if(patt.test(res[i])){
                    res[i] = new Date(res[i]);
                }
            }
        return res;
        }

    };

    this.unwatch = function(name){
        this.watchers[name]();
        sessionStorage.removeItem(name);
        delete this.watchers[name];
    }
});

