app.controller('CreateAwardController', function($scope, SystemMessages, dialog, TournamentService,middleware) {
    middleware.needRol("ADMIN");
    $scope.sendAwardForm = function() {
        console.log($scope.award);
        TournamentService.createAward($scope.$parent.tournament, $scope.award, ()=>{
            SystemMessages.okmessage("Award create!");
            $scope.loadTournament();
            dialog.closeAll();
        }),(a)=>{
            SystemMessages.errormessage("Error creating Tournament");
        }
    }
});