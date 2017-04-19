app.controller('WriteRatingController',function($scope, middleware, ActorService, $routeParams, $rootScope,
                                                SystemMessages, dialog,middleware){
    middleware.needRol("ANY");
    $scope.rateUser = function(){
        ActorService.rate(ActorService.actor.actor.id,$scope.rateform,()=>{});
        $scope.writerating = false;
        $scope.rateform = null;
        ActorService.UserProfile(ActorService.actor.actor.userAccount.username);
        SystemMessages.okmessage("Rating added");
        dialog.closeAll();
    }
});