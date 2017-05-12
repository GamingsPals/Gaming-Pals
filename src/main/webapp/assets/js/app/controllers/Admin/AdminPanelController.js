app.controller('AdminPanelController',function($scope,ActorService,middleware,dialog){
    middleware.needRol("ADMIN,MODERATOR");
    $scope.As = ActorService;
    $scope.As.reportedUsers();
});