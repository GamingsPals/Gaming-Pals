app.controller('AwardsTournamentListController', function($scope, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.awards($scope.tournamentId);
});
