app.controller("TeamController",function($scope,auth,middleware,$routeParams,xhr,TeamService){
    middleware.needRol("ANY");
    $scope.notFound = true;
    TeamService.get($routeParams.name,(a)=>{
        console.log(a);
        $scope.team = a.data.team;
        $scope.tournaments = a.data.tournaments;
        $scope.notFound = true;
    });
});