
app.controller('LolstatsController',function($scope,MatchService,$routeParams,middleware){
    middleware.needRol("ANY");
    $scope.ms = MatchService;
    let id = $routeParams.userid;
    if(typeof $routeParams.userid === "undefined"){
        id = $scope.ActorService.actor.actor.id;
    }
    $scope.ms.getRecentMatches(id);
    $scope.seeMatch = function(matchId,region,m){
        if ( m.showMatch===true){
            m.showMatch = false;
        }else{
            if (m.loaded !== true){
                $scope.ms.getMatch(matchId,region);
                m.loaded = true;
            }
        m.showMatch=true;
        }
    };
    $scope.formatGold = function(gold){
        return gold > 999 ? (gold/1000).toFixed(1) + 'k' : gold
    }
});
