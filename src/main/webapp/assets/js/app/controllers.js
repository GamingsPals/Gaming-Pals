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

app.controller('SearchController',function($scope,ActorService){
    $scope.As = ActorService;
    $scope.As.findAll();
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