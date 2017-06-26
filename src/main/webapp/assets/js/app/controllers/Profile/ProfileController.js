app.controller('ProfileController', function($scope, middleware, ActorService,
											 $routeParams, dialog,GameInfoService,SystemMessages,localization) {
    middleware.needRol("ANY");
	$scope.ActorService = ActorService;
	$scope.ActorService.UserProfile($routeParams.username);

	$scope.viewUsersByTeam = function(teamId) {
		$scope.teamId = teamId;
		dialog.open("viewUsersByTeam", $scope);
	};

	$scope.deleteGameInfo = function(gameInfo){
		GameInfoService.delete(gameInfo,(a)=>{
            SystemMessages.okmessage(localization.profileview.gameidentitydeleted);
            $scope.ActorService.UserProfile($routeParams.username);
        })
	};

	$scope.GameInfoService.addCallbackOnDelete((a)=>{
        $scope.ActorService.UserProfile($routeParams.username);
    });

	$scope.url = "profile/"+$routeParams.username;
	$scope.tabs = {};
	if(typeof $routeParams.tabs !=="undefined"){
        $scope.tabs[$routeParams.tabs] = true;
    }else {
        $scope.tabs.gameprofiles = true;
    }
});
