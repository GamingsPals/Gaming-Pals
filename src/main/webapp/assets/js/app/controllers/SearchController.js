
app.controller('SearchController',function($scope,SearchService,$location){
    $scope.As = SearchService;
    $scope.As.filter($location.search());
});
