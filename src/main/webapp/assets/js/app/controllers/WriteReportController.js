
app.controller('WriteReportController',function($scope, middleware, ActorService, $routeParams, $rootScope, SystemMessages, dialog){
    middleware.needRol("ANY");
    $scope.reportUser = function(){
        ActorService.report(ActorService.actor.actor.id,$scope.reportform,()=>{SystemMessages.okmessage("Report send!");
            dialog.closeAll();});

    }
});