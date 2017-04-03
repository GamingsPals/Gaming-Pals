app.service("TournamentService", function(xhr){
    this.tournaments = {};

    this.getTournaments = function () {
        let object = this;

        xhr.get("api/tournament/list", function (response) {
            object.tournaments = response.data;
        })

    }
});