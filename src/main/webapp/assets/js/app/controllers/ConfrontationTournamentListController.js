app.controller('ConfrontationTournamentListController', function($scope, $routeParams, TournamentService, middleware, dialog) {
	$scope.As = TournamentService;
	$scope.As.controntations($routeParams.tournamentId);
});
