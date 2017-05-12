app.controller('CreateAwardController', function($scope, SystemMessages, dialog, TournamentService,middleware) {
    middleware.needRol("ADMIN");
    $scope.sendAwardForm = function() {
        TournamentService.createAward($scope.$parent.tournament, $scope.awardForm, ()=>{
            SystemMessages.okmessage("Award create!");
            $scope.loadTournament();
            dialog.closeAll();
        }),(a)=>{
            SystemMessages.errormessage("Error sendin Report");
            dialog.closeAll();
        }
    }
});