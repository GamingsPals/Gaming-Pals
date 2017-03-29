app.controller('ReportedUserListController',function($scope,ActorService,middleware,dialog){
    middleware.needRol("ADMIN");
    $scope.As = ActorService;
    $scope.As.reportedUsers();
    $scope.showImage = function(image){
        $scope.image = image;
        dialog.open("showImage",$scope);
    }
});