
app.service("dialog", function(ngDialog){

    this.open = function(template,scope,controller) {
        let path = "assets/html/"+template+".html";
        let options = {};
        options.template = path;
        if(typeof scope !== "undefined") options.scope = scope;
        if(typeof controller !== "undefined") options.controller = controller;
        ngDialog.open(options);
    };

    this.closeAll = function(){
        ngDialog.closeAll();
    }
});