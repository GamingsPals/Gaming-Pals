app.controller('MainController',function($scope,localization,$rootScope,auth,MainPageService,$rootScope){
    localization.init($scope);
    $rootScope.loc = localization;
    $scope.auth = auth;
    $scope.auth.load(function(){});
    $rootScope.csrf = csrf;
    $scope.MainPageService = MainPageService;
    $scope.MainPageService.mainData();
});

app.controller('HomeController',function($scope){
});

app.controller('SearchController',function($scope){
});

app.controller('LoginController',function(middleware){
    middleware.needRol("NONE");
});

app.controller('ProfileController',function($scope,middleware,ActorService,$routeParams,$rootScope){
    $scope.ActorService = ActorService;
    $scope.ActorService.UserProfile($routeParams.username);
    $scope.rateUser = function(){
        ActorService.rate(ActorService.actor.actor.id,$scope.rateform,()=>{},$rootScope.csrf.token);
    }

});