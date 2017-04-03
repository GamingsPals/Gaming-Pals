app.controller('AwardsTournamentListController', function($scope, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.getAwards($scope.$parent.$parent.tournmanentId);
});
