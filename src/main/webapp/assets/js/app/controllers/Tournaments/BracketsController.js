
app.controller("BracketsController",function($scope,TournamentService){
    $scope.ts = TournamentService;

    $scope.getRound = function(i){
        let nrounds = $scope.ts.nrounds($scope.tournament);
        let vround = nrounds - i;
        switch (vround){
            case 0:
                return "Final";
                break;
            case 1:
                return "Semi Finals";
                break;
            case 2:
                return "Quarter Finals";
                break;
            default:
                return "Round i";

        }
    }
});