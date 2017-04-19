app.controller("TeamController",function($scope,auth,middleware,$routeParams,xhr){
    middleware.needRol("ANY");
    $scope.notFound = true;
    xhr.get("api/team/"+$routeParams.name,(a)=>{
        $scope.team = a.data;
        $scope.notFound = true;
    });
});