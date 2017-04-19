app.controller("NotificationsController", function($scope,$routeParams,xhr,middleware){
    middleware.needRol("ANY");
    $scope.mode = $routeParams.mode;

    $scope.accept = function(invitation){
        xhr.get("api/invitations/"+invitation.id+"/accept",(data)=>{
            $scope.notifications.notifications.TeamInvitations
                .splice($scope.notifications.notifications.TeamInvitations
                    .indexOf(invitation),1);
        });
    };
    $scope.reject = function(invitation){
        xhr.get("api/invitations/"+invitation.id+"/reject",(data)=>{
            $scope.notifications.notifications.TeamInvitations
                .splice($scope.notifications.notifications.TeamInvitations
                    .indexOf(invitation),1);
        });
    };
    $scope.setAsRead = function(noti){

    }
});