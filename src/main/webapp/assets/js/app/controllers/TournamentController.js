app.controller("TournamentController",function($scope,auth,middleware,$routeParams,xhr){
    middleware.needRol("USER,ADMIN");
   let id = $routeParams.id;
    $scope.url = "tournament/"+id;
   $scope.notFound = false;
   xhr.get("/api/tournament/"+id,function(data){
       $scope.tournament = data.data;
       $scope.notFound = true;
       console.log("ey");
   },(a)=>{
       $scope.notfound = true;
   })

});