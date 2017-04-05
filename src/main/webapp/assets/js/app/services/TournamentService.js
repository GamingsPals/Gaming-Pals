app.service("TournamentService", function(xhr){
	this.tournaments = {};
	this.confrontations = {};
	this.confrontation = {};
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

	this.assignTeam = function(tournamentId,teamId){
		let object = this;
		xhr.post("api/tournament/assign/"+tournamentId+"/"+teamId,{})
	};

	this.getAwards = function(tournamentId){
		let object = this;
		xhr.get("api/awards/tournament/list?tournamentId="+tournamentId ,function(response){
			object.awards = response.data;
		});
	};

	this.reportMatch = function(confrontation,data,sucess,error){
		let object = this;
		xhr.post("api/user/"+confrontation+"/reportMatch", data,sucess,error);
	};
});