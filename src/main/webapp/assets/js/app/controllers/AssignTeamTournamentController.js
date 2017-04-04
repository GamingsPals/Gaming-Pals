app.controller('AssignTeamTournamentController', function($scope, TournamentService) {
	$scope.As = TournamentService;
	$scope.As.assignTeam($scope.$parent.$parent.tournmanentId, $scope.$parent.$parent.teamId);
});
