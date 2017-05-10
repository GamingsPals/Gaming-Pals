app.controller('CreateTournamentController', function($scope, xhr, $location,middleware,dialog,TournamentService) {
    middleware.needRol("ADMIN");

	$scope.enviarTournamentForm = function() {
		xhr.post("api/createTournament", $scope.tournamentform, function(){
		    dialog.closeAll();
            TournamentService.getTournaments();
            $location.path("/tournament/list");
		});
	}
});
