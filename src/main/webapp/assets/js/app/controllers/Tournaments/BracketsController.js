
app.controller("BracketsController",function($scope,TournamentService,dialog,SystemMessages){
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
    };

    $scope.editTime = function(confrontation){
        $scope.confrontation = confrontation;
        dialog.open("tournaments/confrontationChangeTime",$scope);
    };

    $scope.changeTime = function(confrontation,time){
        console.log(confrontation);
        TournamentService.changeTimeConfrontation(confrontation,time,(a)=>{
            dialog.closeAll();
            $scope.loadTournament();
            SystemMessages.okmessage("Confrontation limit play time changed!")
        });
    }
});