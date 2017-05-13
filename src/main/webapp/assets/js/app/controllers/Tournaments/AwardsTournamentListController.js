app.controller('AwardsTournamentListController', function($scope, TournamentService,middleware) {
    middleware.needRol("ANY");
	$scope.As = TournamentService;
	$scope.As.getAwards($scope.$parent.$parent.tournmanentId);
});
