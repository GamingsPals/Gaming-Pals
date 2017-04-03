app.controller('ConfrontationTournamentListController', function($scope, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.getConfrontations($scope.$parent.$parent.tournmanentId);
});
