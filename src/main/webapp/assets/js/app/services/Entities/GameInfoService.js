app.service("GameInfoService", function(xhr,SystemMessages,ActorService){

    this.callbacksDelete = [];

    this.addCallbackOnDelete = function(callback){
        if(typeof callback!=="undefined"){
            if(this.callbacksDelete.indexOf(callback)!=="undefined"){
                this.callbacksDelete.push(callback);
            }
        }
    };

    this.delete = function(gameInfo,callback){
        let object = this;
        xhr.get("/api/gameinfo/"+gameInfo.id+"/delete",(a)=>{
            if(typeof callback!=="undefined"){
                callback(a);
            }
                closeDropdowns();
                ActorService.UserProfile();
        });
    }
    
});