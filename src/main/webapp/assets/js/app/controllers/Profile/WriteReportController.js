
app.controller('WriteReportController',function($scope, middleware, ActorService, $routeParams, $rootScope,
                                                SystemMessages, dialog,localization){
    middleware.needRol("ANY");
    $scope.reportUser = function(){
        ActorService.report(ActorService.actor.actor.id,$scope.reportform,()=>{
            SystemMessages.okmessage(localization.profileview.reportSended);
            dialog.closeAll();});

    }
});