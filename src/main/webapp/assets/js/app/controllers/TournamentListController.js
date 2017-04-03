app.controller('TournamentListController',function($scope,TournamentService, dialog){
    $scope.Ts = TournamentService;
    $scope.Ts.getTournaments();
    $scope.showLongString = function(longString){
        $scope.longString = longString;
        dialog.open("showLongString",$scope);
    };
    $scope.viewAwards = function(tournmanentId){
        $scope.tournmanentId = tournmanentId;
        dialog.open("viewAwards",$scope);
    }
});