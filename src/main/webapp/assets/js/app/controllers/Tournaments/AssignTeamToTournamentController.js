app.controller("AssignTeamToTournamentController", function($scope,xhr,$location){
    $scope.assignForm = {};
    $scope.added = false;

    $scope.setTeamSelected = function(){
        $scope.teamselected = $scope.userteams.find((a)=>{
            return a.id === +$scope.assignForm.team;
        })
    };
    $scope.assignTeamToTournament = function(){
        if($scope.assignForm.team !==""){
            xhr.get(`api/tournament/assign/${$scope.tournament.id}/${$scope.assignForm.team}`,function(data){
                $scope.added = true;
                $location.path("tournament/"+$scope.tournament.id+"/bracket");
            })
        }
    }
});