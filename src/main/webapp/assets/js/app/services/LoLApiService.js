app.service("LolApiService",function(xhr){
    this.main = {};

    this.mainData = function(summoner,success,error){
        let object = this;
        xhr.get("api/lol/addsummoner/"+summoner.summoner+"/"+summoner.region+"?key="+summoner.key,function(data){
            if(typeof success !=="undefined")
                success(data);
        },function(data){
            if(typeof error !=="undefined")
                error(data);
        })
    };

});