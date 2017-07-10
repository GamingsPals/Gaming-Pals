app.controller('WriteReportMatchController', function($scope, SystemMessages, dialog, TournamentService,middleware) {
    middleware.needRol("ANY");
    $scope.sendReportMatchForm = function() {
        $scope.reportMatch.team = $scope.matchtoreport.team.id;
        TournamentService.reportMatch($scope.matchtoreport.confrontation.id,$scope.reportMatch,()=>{
            SystemMessages.okmessage(localization.tournament.reportsend);
            $scope.loadTournament();
            $scope.error = false;
            dialog.closeAll();
        },(a)=>{
            $scope.error = true;
            SystemMessages.errormessage(localization.tournament.errorsendingreport);
            dialog.closeAll();
        });
    }
});
