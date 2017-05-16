app.service("GameService",function(xhr){
    this.main = {};

    this.all = function(success,error){
        let object = this;
        xhr.get("api/games/all",function(data){
            if(typeof success !=="undefined")
                success(data);
        },function(data){
            if(typeof error !=="undefined")
                error(data);
        })
    };




});