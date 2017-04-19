app.controller('AssignTeamTournamentController', function($scope, TournamentService, $routeParams,middleware) {
    middleware.needRol("ANY");
	$scope.As = TournamentService;
	console.log($routeParams.tournamentId);
	console.log($routeParams.teamId);
	$scope.As.assignTeam($routeParams.tournamentId, $routeParams.teamId);
});
