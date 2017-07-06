
app.controller('SearchController',function($scope,SearchService,$location,middleware, GameService, UserService){
    middleware.needRol("ANY");
    $scope.As = UserService;
    $scope.search = {"pepe":1};

    $scope.As.addCallback((a)=>{
        $scope.users = a;
    });


    $scope.GameInfoService.addCallbackOnDelete((a)=>{
        $scope.As.findAll((a)=>{
            $scope.users = a;
        });
    });

    GameService.all((a)=>{
        $scope.games = a.data;
    });

});
