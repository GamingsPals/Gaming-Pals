
app.controller('SearchController',function($scope,SearchService,$location,middleware, GameService, LanguageService){
    middleware.needRol("ANY");
    $scope.As = SearchService;
    $scope.search = {};
    $scope.page = (typeof $location.search().page!=="undefined") ? $location.search().page : 1;
    $scope.limit = 6;
    $scope.As.filter($location.search());
    $scope.search = $location.search();
    $scope.filter = function(object){
        object.page=1;
        delete object._csrf;
        for(let i in object){
            if(object.hasOwnProperty(i)){
                $location.search(i,object[i]);
            }
        }
    };
    GameService.all((a)=>{
        $scope.games = a.data;
    });
    LanguageService.getAll(function(data){
        $scope.languages = data;
    });

    $location.search("_csrf",null);
});
