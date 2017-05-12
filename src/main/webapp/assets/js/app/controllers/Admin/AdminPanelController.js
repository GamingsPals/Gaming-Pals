app.controller('AdminPanelController',function($scope,ActorService,middleware,AdminService,$routeParams,$location){
    middleware.needRol("ADMIN,MODERATOR");
    $scope.getBannedUsers = ()=>{
        AdminService.getBannedUsers((a)=>{
            $scope.bannedusers = a;
        });
    };
    AdminService.addHookBannedUser((actor)=>{
        $scope.getBannedUsers();
    });

    AdminService.getReportedUsers((a)=>{
        $scope.reportedusers = a;
    });

    $scope.getBannedUsers();
    $scope.mode = $routeParams.menu;
    console.log($scope.mode);
    if(typeof $scope.mode ==="undefined"){
        $location.path("adminpanel/usersbanned");
    }


});