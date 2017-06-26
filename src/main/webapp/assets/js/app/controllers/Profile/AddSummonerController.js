app.controller('AddSummonerController',function($scope,LolApiService,dialog,ActorService,middleware,localization){
    middleware.needRol("ANY");
    $scope.LolData=LolApiService;
    $scope.validateSummoner = function(){
        if (typeof $scope.search.summoner!=="undefined" && typeof $scope.search.region!=="undefined"){
            $scope.check = true;
            $scope.search.key = md5($scope.search.summoner.toLowerCase());
            $scope.search.key = $scope.search.key.substring(0, 25);
        }
    };
    $scope.addSummoner = function(){
        $scope.LolData.mainData($scope.search,function(){
            dialog.closeAll();
            $scope.error = false;
            ActorService.UserProfile();
        },function(data){
            $scope.error = localization.addSummoner[data.data.message];
        });
    }
});