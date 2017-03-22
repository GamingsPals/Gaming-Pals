app.controller('MainController',function($scope,localization,$rootScope,auth,MainPageService,MessageSystem,$sanitize){
    localization.init($scope);
    $rootScope.loc = localization;
    $scope.auth = auth;
    $scope.auth.load(function(){});
    $rootScope.csrf = csrf;
    $scope.MainPageService = MainPageService;
    $scope.MainPageService.mainData();
    $scope.MessageSystem = MessageSystem;
    $scope.sanitize = $sanitize;
});

app.controller('HomeController',function($scope){
});

app.controller('SearchController',function($scope,SearchService){
    $scope.As = SearchService;
    $scope.As.findAll();
    $scope.filter = function(filter){
        $scope.As.filter(filter);
    }
});

app.controller('ReportedUserListController',function($scope,ActorService,middleware,dialog){
    middleware.needRol("ADMIN");
    $scope.As = ActorService;
    $scope.As.reportedUsers();
    $scope.showImage = function(image){
        $scope.image = image;
        dialog.open("showImage",$scope);
    }
});


app.controller('LoginController',function(middleware){
    middleware.needRol("NONE");
});

app.controller('ProfileController',function($scope,middleware,ActorService,$routeParams){
    $scope.ActorService = ActorService;
    $scope.ActorService.UserProfile($routeParams.username);
});

app.controller('WriteRatingController',function($scope,middleware,ActorService,$routeParams,$rootScope,MessageSystem,dialog){
    $scope.rateUser = function(){
        ActorService.rate(ActorService.actor.actor.id,$scope.rateform,()=>{});
        $scope.writerating = false;
        $scope.rateform = null;
        ActorService.UserProfile(ActorService.actor.actor.userAccount.username);
        MessageSystem.okmessage("Rating added");
        dialog.closeAll();
    }
});


app.controller('WriteReportController',function($scope,middleware,ActorService,$routeParams,$rootScope,MessageSystem,dialog){
    $scope.reportUser = function(){
        ActorService.report(ActorService.actor.actor.id,$scope.reportform,()=>{MessageSystem.okmessage("Report send!");
            dialog.closeAll();});

    }
});
app.controller('LolApiController',function($scope,LolApiService,$routeParams,$window){
	$scope.LolData=LolApiService;
	$scope.LolData.mainData($routeParams.name,$routeParams.region);
	$scope.searchSummoner=function(){
		$window.location.href = 'lol/user/'+$scope.search.summoner+"/"+$scope.search.region;
	};
});
app.controller('LolApiVinculatedController',function($scope,LolApiVinculatedService,$routeParams,$window){
	$scope.LolData=LolApiVinculatedService;
	console.log($scope);
	$scope.LolData.mainData($routeParams.idSummoner,$routeParams.region);
	$scope.vinculatedSummoner=function(){
		$scope.LolData.getMastery($routeParams.idSummoner,$routeParams.region);
		angular.forEach($scope.LolData.mainMastery, function(value, key){
		     if(value.name==$scope.LolData.main.keyLol){
		    	$scope.LolData.saveSummoner($routeParams.idSummoner,$routeParams.region);
		    	$window.location.href = 'lol/user/';
		     }
		});
		
	};
});