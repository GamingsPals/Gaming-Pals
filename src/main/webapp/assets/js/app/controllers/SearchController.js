
app.controller('SearchController',function($scope,SearchService,$location,middleware, GameService, LanguageService){
    middleware.needRol("ANY");
    $scope.As = SearchService;
    $scope.search = {};
    $scope.search.page = 1;
    $scope.As.filter($location.search());
    $scope.search = $location.search();
    $scope.filter = function(object){
        for(let i in object){
            if(object.hasOwnProperty(i)){
                $location.search(i,object[i]);
            }
        }
    };

    GameService.all((a)=>{
        $scope.games = a.data;
        console.log($scope.games);
    });
    LanguageService.getAll(function(data){
        $scope.languages = data;
    });

});
