app.controller("GameController",function($scope,auth,middleware,$routeParams,xhr){
    middleware.needRol("USER,ADMIN");
    $scope.auth = auth;
    let id = $routeParams.id;
    $scope.url = "game/"+id;
    $scope.mode = $routeParams.menu;
   if(typeof $scope.mode ==="undefined"){
       $scope.mode = "users";
   }
    $scope.tabs=$scope.mode;
    xhr.get("/api/game/"+id,(a)=>{
        $scope.game = a.data.game;
        $scope.tournaments = a.data.tournaments;
        $scope.usersgame = a.data.users;
        $scope.notFound = true;
    })
});