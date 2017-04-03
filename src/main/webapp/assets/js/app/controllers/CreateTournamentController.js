app.controller('CreateTournamentController', function($scope, xhr, $location) {
	$scope.enviarTournamentForm = function() {
		xhr.post("api/createTournament", $scope.tournamentform);
		$location.path("/tournament/list");
	}
});
