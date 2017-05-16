app.service("MatchService",function(xhr,auth){
    this.matches = {};
    this.summoner = {};

    this.getRecentMatches = function(id){
        let object = this;
        xhr.get(`api/lol/stats/${id}`,function(data){
            object.matches = data.data.matches;
            object.summoner = data.data.summoner;
        })
    };

    this.getMatch = function(id,region){
        let object = this;
        xhr.get(`api/lol/stats/match/${id}?region=${region}`,function(data){
            let game = object.matches.forEach((e,k)=>{
                if (e.gameId === id){
                    e.match = data.data;
                    object.matches[k] = e;
                }
            })
        })
    }
});