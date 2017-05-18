app.service("TournamentService", function(xhr,Alerts,SystemMessages,localization,auth,subscriber){
	this.tournaments =[];
	this.confrontations = [];
	this.confrontation = [];
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
            subscriber.set("tournament",data.data);
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

    this.changeTimeConfrontation = function(confrontation,time,callback){
        if(auth.hasRole('ADMIN')){
        let timer = +new Date(time).getTime();
        console.log(time);
            xhr.get(`/api/admin/confrontation/${confrontation.id}?limitTime=${timer}`,callback);
        }
    };

	this.getIncidences = function(tournament,callback){
	    if(auth.hasRole("Admin")){
	        xhr.get(`/api/admin/tournament/${tournament}/incidences`,(a)=>{
	            subscriber.set("tournament:incidences",a.data);
	            callback(a.data);
            })
        }
    };


    this.resolveConfrontation = function(confrontation,tournament,team){
        xhr.get(`api/admin/tournament/${tournament.id}/confrontation/${confrontation.id}/winner/${team}`,(a)=>{
            subscriber.set("tournament:resolve",a.data);
        })
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

	this.canBeDeleted = function(tournament){
	    if(typeof tournament==="undefined") return false;

	    return new Date(tournament.limitInscription)>new Date();
    };

	this.getAwards = function(tournamentId){
		let object = this;
		xhr.get("api/awards/tournament/list?tournamentId="+tournamentId ,function(response){
			object.awards = response.data;
		});
	};

	this.delete = function(tournament,callback){
	    if(this.canBeDeleted(tournament)===false) return false;
        let data = {};
        let args = {"tournament": tournament.title};
        data.title = localization.eval(localization.tournament.admin.suretodelete,args);
        data.confirmtitle = localization.deleted+"!";
        data.confirmtext = localization.eval(localization.tournament.admin.deleted,args);
        let object = this;
        data.callback = ()=>{
            xhr.get("api/tournament/"+tournament.id+"/delete" ,function(response){
                object.getTournaments();
                SystemMessages.okmessage(data.confirmtext);
                if(typeof callback !=="undefined") callback();
            });
        };
        Alerts.confirm(data);
    };

	this.reportMatch = function(confrontation,data,sucess,error){
		let object = this;
		xhr.post("api/user/"+confrontation+"/reportMatch", data,sucess,error);
	};

    this.createAward = function(tournament, data, sucess, error){
        let object = this;
        xhr.post("api/awards/"+tournament.id+"/create", data,sucess,error);
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