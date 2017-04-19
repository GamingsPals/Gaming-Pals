
app.service("dialog", function(ngDialog,$rootScope){

    this.open = function(template,scope,controller) {
        let path = "assets/html/"+template+".html";
        let options = {};
        options.template = path;
        if(typeof scope !== "undefined") options.scope = scope;
        if(typeof controller !== "undefined") options.controller = controller;
        this.dialog = ngDialog.open(options);
        return this.dialog;
    };

    this.closeAll = function(){
        ngDialog.closeAll();
    };

    this.close = function(dialog){
        ngDialog.close(dialog);
    };

    this.redirect = function(dialog,callback){
        dialog.closePromise.then(function(data){
            $rootScope.back();
            if(typeof callback !=="undefined"){
                callback(data);
            }
        })
    }
});