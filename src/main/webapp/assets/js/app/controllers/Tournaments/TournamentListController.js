app.controller('TournamentListController',function($scope,TournamentService, dialog,middleware){
    middleware.needRol("ANY");
    $scope.Ts = TournamentService;
    $scope.Ts.getTournaments();
    $scope.viewAwards = function(tournmanentId){
        $scope.tournmanentId = tournmanentId;
        dialog.open("viewAwards",$scope);
    };

});