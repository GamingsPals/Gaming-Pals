app.controller('WriteRatingController',function($scope, middleware, ActorService, $routeParams, $rootScope,
                                                SystemMessages, dialog){
    middleware.needRol("ANY");
    $scope.rateUser = function(){
        ActorService.rate(ActorService.actor.actor.id,$scope.rateform,()=>{
            ActorService.UserProfile(ActorService.actor.actor.userAccount.username);
        });
        $scope.writerating = false;
        $scope.rateform = null;
        SystemMessages.okmessage("Rating added");
        dialog.closeAll();
    }
});