app.service("TournamentService", function(xhr){
    this.tournaments = {};
    this.confrontations = {};
    this.getTournaments = function () {
        let object = this;
        xhr.get("api/tournament/list", function (response) {
            object.tournaments = response.data;
        })

    }
    
    this.getConfrontations = function(tournamentId){
        let object = this;
        xhr.get("api/confrontations/tournament/list?tournamentId="+tournamentId ,function(response){
            object.confrontations = response.data;
        });
    };
    
    this.getAwards = function(tournamentId){
        let object = this;
        xhr.get("api/awards/tournament/list?tournamentId="+tournamentId ,function(response){
            object.awards = response.data;
        });
    };
});