app.service("GameInfoService", function(xhr,SystemMessages){

    this.callbacksDelete = [];

    this.addCallbackOnDelete = function(callback){
        if(typeof callback!=="undefined"){
            this.callbacksDelete.push(callback);
        }
    };

    this.delete = function(gameInfo,callback){
        console.log(gameInfo);
        let object = this;
        xhr.get("/api/gameinfo/"+gameInfo.id+"/delete",(a)=>{
            if(typeof callback!=="undefined"){
                callback(a);
            }
                closeDropdowns();
                object.callbacksDelete.forEach((i)=>{
                    if(typeof i==="function") {
                        i(a);
                    }
                });
        });
    }
    
});