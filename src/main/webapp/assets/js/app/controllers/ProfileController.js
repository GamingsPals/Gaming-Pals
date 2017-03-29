
app.controller('ProfileController',function($scope,middleware,ActorService,$routeParams){
    $scope.ActorService = ActorService;
    $scope.ActorService.UserProfile($routeParams.username);
});