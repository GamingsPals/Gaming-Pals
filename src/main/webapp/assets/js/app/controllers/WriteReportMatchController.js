app.controller('WriteReportMatchController', function($scope, SystemMessages, dialog, TournamentService,middleware) {
    middleware.needRol("ANY");
    $scope.sendReportMatchForm = function() {
        console.log($scope.reportMatchForm);
        console.log($scope.$parent.$parent.confrontationId);
        TournamentService.reportMatch($scope.$parent.$parent.confrontationId,$scope.reportMatchForm,()=>{SystemMessages.okmessage("Report send!");
            dialog.closeAll();});
    }
});
