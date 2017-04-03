app.controller('TournamentListController',function($scope,TournamentService){
    $scope.Ts = TournamentService;
    $scope.Ts.getTournaments();
});