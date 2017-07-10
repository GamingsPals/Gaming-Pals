
app.controller('SearchController',function($scope,SearchService,$location,middleware, GameService, UserService){
    middleware.needRol("ANY");
    $scope.As = UserService;
    let search = $location.search();
    $scope.search = {};
    if(typeof search.username!=="undefined"){
        $scope.search.userAccount={};
        $scope.search.userAccount.username = search.username;
    }

    $scope.As.addCallback((a)=>{
        $scope.users = a;
    });

    $scope.As.findAll();


    $scope.GameInfoService.addCallbackOnDelete((a)=>{
        $scope.As.findAll((a)=>{
            $scope.users = a;
        });
    });

    GameService.all((a)=>{
        $scope.games = a.data;
    });

});
