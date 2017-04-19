app.controller('ProfileController', function($scope, middleware, ActorService, $routeParams, dialog) {
    middleware.needRol("ANY");
	$scope.ActorService = ActorService;
	$scope.ActorService.UserProfile($routeParams.username);

	$scope.viewUsersByTeam = function(teamId) {
		$scope.teamId = teamId;
		dialog.open("viewUsersByTeam", $scope);
	};

	$scope.url = "profile/"+$routeParams.username;
	$scope.tabs = {};
	if(typeof $routeParams.tabs !=="undefined"){
        $scope.tabs[$routeParams.tabs] = true;
    }else {
        $scope.tabs.gameprofiles = true;
    }
});
