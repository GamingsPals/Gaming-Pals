

app.service("SearchService", function(xhr){
    this.search = [];

    this.filter = function(filter,callback){
        let object = this;
        xhr.post("api/search",filter,function(data){
            object.search = data.data;
            if (typeof callback !== "undefined") callback();
        })
    };

    this.findAll = function(){
        let object = this;
        xhr.get("api/search",function(data){
            object.search = data.data;
        })
    };

});