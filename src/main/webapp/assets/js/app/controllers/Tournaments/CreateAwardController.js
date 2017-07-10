app.controller('CreateAwardController', function($scope, SystemMessages, dialog, TournamentService,middleware,localization) {
    middleware.needRol("ADMIN");
    $scope.sendAwardForm = function() {
        TournamentService.createAward($scope.$parent.tournament, $scope.award, ()=>{
            SystemMessages.okmessage(localization.tournament.awardcreated);
            $scope.loadTournament();
            $scope.error = undefined;
            dialog.closeAll();
        },(a)=>{
            $scope.error = true;
            console.log("Ey");
            SystemMessages.errormessage(localization.tournament.errorcreatingaward);
        });
    }
});