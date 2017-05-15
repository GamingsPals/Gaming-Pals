app.controller("DashBoardController", function($scope,xhr){
    $scope.stats = ()=>{
        xhr.get("api/main",(a)=>{
            $scope.lastTournaments = a.data.lastTournaments;
            $scope.bestRatedUsers = a.data.bestRatedUsers;
            $scope.games = a.data.games;
        })
    };

    $scope.stats();


});