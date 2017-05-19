app.controller("TournamentController",function($scope,auth,middleware,$routeParams,xhr, TournamentService,$location){
    middleware.needRol("USER,ADMIN");
    $scope.auth = auth;
    $scope.TournamentService = TournamentService;
    let id = $routeParams.id;
    $scope.matchtoreport = false;
    $scope.loadTournament = function(){
        TournamentService.getTournament(id, function(data){
            $scope.tournament = data.data;
            $scope.notFound = true;
            if(auth.hasRole('ADMIN')){

            TournamentService.getIncidences($scope.tournament.id,(a)=>{
                $scope.incidences = a;
            });
            }
            xhr.get("api/user/teams/"+data.data.id,function(response){
                $scope.userteams = response.data;
                $scope.userteams = $scope.userteams.filter((a)=>{
                    let result = true;
                    $scope.tournament.teams.forEach((b)=>{
                        if(b.id === a.id){
                            result = false;
                        }
                    });
                    return result;
                });
                if($scope.userteams.length===0){
                    $scope.added = true;
                }
            });
            TournamentService.getConfrontationsAvailable($scope.tournament,(a)=>{
                if(a.confrontation!==null && a.team !== null){
                    $scope.matchtoreport = a;
                }

            })
        },(a)=>{
            $scope.notFound = false;
        });
    };
    $scope.loadTournament();
    $scope.url = "tournament/"+id;
    $scope.mode = $routeParams.menu;
    $scope.getTeamToPlay = function(){
        let team = null;
        $scope.matchtoreport.confrontation.participes.forEach((a)=>{
            if(a.team.id!==$scope.matchtoreport.team.id){
                team = a.team;
                return false;
            }
        });
        return team;
    };
   if(typeof $scope.mode ==="undefined"){
       $scope.mode = "resume";
   }
    $scope.tabs=$scope.mode;


   $scope.getTeamByPrincipal = function(){
       let principal = auth.principal.actor;
       let result = false;
       if(typeof $scope.tournament==="undefined") return false;
       $scope.tournament.teams.forEach((a)=>{
           a.users.forEach((u)=>{
               if(u.id===principal.id){
                   result = a;
                   return false;
               }
           });
           if(result===true){
               return false;
           }
       });

       return result;
   };

   $scope.advanceRound = function(tournament){
       TournamentService.advanceRound(tournament,(a)=>{
           $scope.loadTournament();
       });
    };

   $scope.delete = function(tournament){
       TournamentService.delete(tournament,(a)=>{
           $location.path("tournament/list");
       })
   };

    $scope.getStyleFromResult = function(tournament,id){
       let result = false;
       for(let i of tournament.participes){
           if(i.winner === true) result = true;
       }
       if(!result || typeof tournament.participes[id] === "undefined") return '';
        return (tournament.participes[id].winner===true) ? 'winner' : 'looser';
    }
});