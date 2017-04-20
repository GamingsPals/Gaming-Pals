app.controller("TournamentController",function($scope,auth,middleware,$routeParams,xhr, TournamentService){
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
                    console.log("adsd");
                    $scope.matchtoreport = a;
                }

            })
        },(a)=>{
            $scope.notfound = true;
        });
    };
    $scope.loadTournament();
    $scope.url = "tournament/"+id;
   $scope.notFound = false;
    $scope.mode = $routeParams.menu;
   if(typeof $scope.mode ==="undefined"){
       $scope.mode = "resume";
   }
    $scope.tabs=$scope.mode;

   $scope.advanceRound = function(tournament){
       TournamentService.advanceRound(tournament,(a)=>{
           $scope.loadTournament();
       });
    }
});