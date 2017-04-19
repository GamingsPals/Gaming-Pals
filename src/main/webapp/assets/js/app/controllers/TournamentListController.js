app.controller('TournamentListController',function($scope,TournamentService, dialog,middleware){
    middleware.needRol("ANY");
    $scope.Ts = TournamentService;
    $scope.Ts.getTournaments();
    $scope.showLongString = function(longString){
        $scope.longString = longString;
        dialog.open("showLongString",$scope);
    };
    $scope.viewAwards = function(tournmanentId){
        $scope.tournmanentId = tournmanentId;
        dialog.open("viewAwards",$scope);
    };
    $scope.viewConfrontation = function(tournmanentId){
        $scope.tournmanentId = tournmanentId;
        dialog.open("viewConfrontation",$scope);
    };

    $scope.assignTeamToTournament = function (t) {
        TournamentService.selectedTournament = t;
        dialog.open("assignTeamToTournament");
    };

    $scope.closedTournament = function (tournament) {
        return tournament.teams.length===+tournament.numberTeams || $scope.startedTournament(tournament);
    };

    $scope.startedTournament = function (tournament) {
        let now = new Date();
        let limit = new Date(tournament.limitInscription);

        return limit < now;
    }
});