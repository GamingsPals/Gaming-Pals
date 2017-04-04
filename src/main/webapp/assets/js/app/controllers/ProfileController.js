app.controller('ProfileController', function($scope, middleware, ActorService, $routeParams, dialog) {
	$scope.ActorService = ActorService;
	$scope.ActorService.UserProfile($routeParams.username);

	$scope.viewUsersByTeam = function(teamId) {
		$scope.teamId = teamId;
		dialog.open("viewUsersByTeam", $scope);
	};

});
