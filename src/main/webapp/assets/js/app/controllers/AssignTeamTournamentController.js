app.controller('AssignTeamTournamentController', function($scope, TournamentService, $routeParams,middleware) {
    middleware.needRol("ANY");
	$scope.As = TournamentService;
	$scope.As.assignTeam($routeParams.tournamentId, $routeParams.teamId);
});
