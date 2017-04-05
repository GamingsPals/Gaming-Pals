app.controller('ConfrontationTournamentListController', function($scope, TournamentService, dialog, auth) {
	$scope.As = TournamentService;
	$scope.As.getConfrontations($scope.$parent.$parent.tournmanentId);

    $scope.reportMatch = function(confrontationId){
        $scope.confrontationId = confrontationId;
        dialog.open("reportMatch",$scope);
    }

    $scope.canShowReport = function (confrontation) {
        $scope.confrontation = confrontation;
        let actor = auth.principal.actor;
        let user;
        let result = false;
        confrontation.participes.forEach((p)=> {
            user = p.team.users.find((u) => {
                return u.id == actor.id
            });
            if(typeof user !=="undefined"){
                result = true;
                return false;
            }
        });
        console.log(user);
        console.log(actor);
        console.log(result);
        return result;
    }
});
