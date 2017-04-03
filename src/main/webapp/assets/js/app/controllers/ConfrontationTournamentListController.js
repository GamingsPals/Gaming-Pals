app.controller('ConfrontationTournamentListController', function($scope, $routeParams, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.confrontations($routeParams.tournamentId);
});
