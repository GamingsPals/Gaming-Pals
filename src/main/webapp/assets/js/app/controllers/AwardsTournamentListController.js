app.controller('AwardsTournamentListController', function($scope, $routeParams, TournamentService, middleware, dialog) {
	$scope.As = TournamentService;
	$scope.As.awards($routeParams.tournamentId);
});
