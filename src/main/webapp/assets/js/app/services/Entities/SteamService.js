app.service("SteamService", function(xhr,auth,$rootScope){
    this._all = [];
    this.loaded = false;

    this.all = function(search){
        let object = this;
        xhr.get("/api/admin/games/steam/all?search="+search,(a)=>{
            object._all = a.data;
        })
    };

    this.addGame =function(game,success,error){
        xhr.post("/api/admin/games/steam/add",game,success,error);
    }
});