
app.controller("BracketsController",function($scope,TournamentService,dialog,SystemMessages,localization){
    $scope.ts = TournamentService;
    let loc = localization;
    $scope.getRound = function(i){
        let nrounds = $scope.ts.nrounds($scope.tournament);
        let vround = nrounds - i;
        switch (vround){
            case 0:
                return loc.tournament.brackets.finals;
                break;
            case 1:
                return loc.tournament.brackets.semifinals;
                break;
            case 2:
                return loc.tournament.brackets.quarterfinals;
                break;
            default:
                return loc.tournament.brackets.round+" "+vround;

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