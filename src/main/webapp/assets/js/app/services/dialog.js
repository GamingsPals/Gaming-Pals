
app.service("dialog", function(ngDialog,$rootScope){

    this.templates = [];
    this.open = function(template,scope,controller) {
        this.dialog = {};
        let path = "assets/html/"+template+".html";
        let options = {};
        options.closeByDocument = false;
        options.template = path;
        if(typeof scope !== "undefined") {
            options.scope = scope;
        }
        if(typeof controller !== "undefined") options.controller = controller;
        if(!this.opened(template)){
            this.dialog = ngDialog.open(options);
            this.templates.push({"template":template,"dialog":this.dialog});
            this.onCloseUnShift(this.dialog);
        }else{
            this.dialog = this.getDialog(template).dialog;
        }
        return this.dialog;
    };

    this.onCloseUnShift = function(dialog){
        let object = this;
        dialog.closePromise.then((a)=>{
            object.templates.splice(-1,1);
        })
    };

    this.getDialog = function(template){
        return this.templates.find((a) => {
            return a.template === template;
        });
    };

    this.opened = function(dialog){
        let result = true;
        let element = this.templates.find((a)=>{
            return a.template === dialog;
        });
        return typeof element!=="undefined";
    };

    this.isOpen = function(id){
        return ngDialog.isOpen(id);
    };

    this.closeAll = function(){
        ngDialog.closeAll();
        this.templates=[];
    };

    this.close = function(dialog,callback){
        ngDialog.close(dialog);
        if(typeof callback!=="undefined"){
            dialog.closePromise.then((a)=>{
                callback();
            })
        }
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