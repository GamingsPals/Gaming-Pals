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
            xhr.get("api/user/teams",function(response){
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
   if(typeof $scope.mode ==="undefined"){
       $scope.mode = "resume";
   }
    $scope.tabs=$scope.mode;

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
           if(i.isWinner === true) result = true;
       }
       if(!result) return '';
        return (tournament.participes[id].isWinner===true) ? 'winner' : 'looser';
    }
});