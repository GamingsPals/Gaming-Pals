
app.controller('LolstatsController',function($scope,MatchService,$routeParams,ActorService){
    $scope.ms = MatchService;
    let id = $routeParams.userid;
    if(typeof $routeParams.userid === "undefined"){
        id = $scope.ActorService.actor.actor.id;
    }
    $scope.ms.getRecentMatches(id);
});
