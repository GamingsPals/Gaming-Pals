app.service("TournamentService", function(xhr){
	this.tournaments = {};
	this.confrontations = {};
	this.confrontation = {};
	this.getTournaments = function () {
		let object = this;
		xhr.get("api/tournament/list", function (response) {
			object.tournaments = response.data;
		})

	};

	this.getTournament = function(id,sucess,error){
	    let o = this;
        xhr.get("api/tournament/"+id,function(data){
        	o.tournament = data;
            if(typeof sucess!=="undefined"){
                sucess(data);
            }
        },(a)=>{
            if(typeof error!=="undefined"){
                error(a);
            }
        });
	};


	this.isInscriptionOver = function(tournament){
	    if(typeof tournament!=="undefined"){
        let date = new Date();
        let tdate = new Date(tournament.limitInscription);
        return tdate<date;
        }
    };

	this.fullInscription = function(tournament){
        if(typeof tournament==="undefined"){
            return false;
        }
        return tournament.teams.length==tournament.numberTeams
    };


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

	this.advanceRound = function (tournament,callback) {
        let object = this;
        xhr.get("api/tournament/advanceRound/"+tournament.id,(a)=>{
            callback(a);
        });
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

    this.numberOfRounds = function(tournament){
        if(typeof tournament==="undefined"){
            return [];
        }
        let result = [];
        let numberRounds = parseInt(this.getBaseLog(2,tournament.numberTeams));
        for(let i=1;i<=numberRounds;i++){
            result.push(i);
        }
        return result;
    };

    this.nrounds = function(tournament){
        if(typeof tournament==="undefined"){
            return 0;
        }
        return  parseInt(this.getBaseLog(2,tournament.numberTeams));
    };

   this.numberOfConfrontation = function(i,tournament){
       if(typeof tournament==="undefined"){
           return [];
       }
        let result = [];
        let numberTeams = tournament.numberTeams;
        let total = parseInt(numberTeams / Math.pow(2,i));
        for(let i=1;i<=total;i++){
            result.push(i);
        }

        return result;
    };

    this.getMatch = function(r,n,tournament){
        if(typeof tournament==="undefined") return [];
        return tournament.confrontations.find((a)=>{
            return a.numberMatch == n && a.round == r;
        })
    };


    this.getBaseLog = function (x, y) {
        return Math.log(y) / Math.log(x);
    };

    this.getConfrontationsAvailable = function(tournament,callback){
        xhr.get("api/tournament/matchtoreport/"+tournament.id,(a)=>{
            if(typeof callback!=="undefined"){
                callback(a.data);
            }
        })
    }
});