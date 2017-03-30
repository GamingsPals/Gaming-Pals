app.service("MatchService",function(xhr,auth){
    this.matches = {};
    this.summoner = {};

    this.getRecentMatches = function(id){
        let object = this;
        xhr.get(`api/lol/stats/${id}`,function(data){
            object.matches = data.data.matches;
            object.summoner = data.data.summoner;
        })
    }

});