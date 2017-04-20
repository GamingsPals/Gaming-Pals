app.controller('WriteReportMatchController', function($scope, SystemMessages, dialog, TournamentService,middleware) {
    middleware.needRol("ANY");
    $scope.sendReportMatchForm = function() {
        $scope.reportMatch.team = $scope.matchtoreport.team.id;
        TournamentService.reportMatch($scope.matchtoreport.confrontation.id,$scope.reportMatch,()=>{
            SystemMessages.okmessage("Report send!");
            $scope.loadTournament();
            dialog.closeAll();
        },(a)=>{
            SystemMessages.errormessage("Error sendin Report");
            dialog.closeAll();
        });
    }
});
