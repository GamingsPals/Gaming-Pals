
app.controller('SearchController',function($scope,SearchService,$location,middleware){
    middleware.needRol("ANY");
    $scope.As = SearchService;
    $scope.As.filter($location.search());
    $scope.search = $location.search();
    $scope.filter = function(object){
        for(let i in object){
            if(object.hasOwnProperty(i)){
                $location.search(i,object[i]);
            }
        }
    }
});
