app.controller("DashBoardController", function($scope,xhr,DashBoardService){
    $scope.stats = ()=>{
        DashBoardService.getDashboardData((a)=>{
            $scope.lastTournaments = a.lastTournaments;
            $scope.bestRatedUsers = a.bestRatedUsers;
            $scope.games = a.games;
        })
    };

    $scope.stats();


});