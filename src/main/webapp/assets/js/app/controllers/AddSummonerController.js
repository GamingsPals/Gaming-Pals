app.controller('AddSummonerController',function($scope,LolApiService,dialog,ActorService){
    $scope.LolData=LolApiService;
    $scope.test =" Asdad";
    console.log("adsda");
    $scope.validateSummoner = function(){
        console.log($scope.search);
        if (typeof $scope.search.summoner!=="undefined" && typeof $scope.search.region!=="undefined"){
            $scope.check = true;
            $scope.search.key = md5($scope.search.summoner);
            $scope.search.key = $scope.search.key.substring(0, 25);
        }
    };
    $scope.addSummoner = function(){
        $scope.LolData.mainData($scope.search,function(){
            dialog.closeAll();
            $scope.error = false;
            ActorService.UserProfile();
        },function(data){
            console.log(data);
            $scope.error = data.data.message;
        });
    }
});