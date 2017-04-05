app.controller('AssignTeamTournamentController', function($scope, TournamentService, $routeParams) {
	$scope.As = TournamentService;
	console.log($routeParams.tournamentId);
	console.log($routeParams.teamId);
	$scope.As.assignTeam($routeParams.tournamentId, $routeParams.teamId);
});
