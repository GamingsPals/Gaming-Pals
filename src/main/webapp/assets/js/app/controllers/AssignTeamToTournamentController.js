app.controller("AssignTeamToTournamentController", function($scope,xhr,$location,dialog,TournamentService){
    $scope.tournament = TournamentService.selectedTournament;
    xhr.get("api/user/teams",function(response){
        $scope.teams = response.data;
    });
    $scope.setTeamSelected = function(){
        $scope.teamselected = $scope.teams.find((a)=>{
            return a.id == $scope.assignForm.team;
        })
    };
    $scope.assignTeamToTournament = function(){
        console.log($scope.assignForm);
        if($scope.assignForm.team !==""){
            xhr.get(`api/tournament/assign/${$scope.tournament.id}/${$scope.assignForm.team}`,function(data){
                dialog.closeAll();
                $location.path("/tournament/list");
            })
        }
    }
});