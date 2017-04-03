app.service("TournamentService", function(xhr){
    this.tournaments = [];

    this.getTournaments = function () {
        let object = this;

        xhr.get("api/tournaments/all", function (data) {
            object.tournaments = data.data;
        })

    }
});