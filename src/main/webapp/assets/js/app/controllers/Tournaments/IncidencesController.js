app.controller("IncidencesController", function($scope,TournamentService,subscriber,dialog,SystemMessages){


    $scope.resolveDialog = function(a){
       $scope.reportMatch =  a;
       dialog.open("tournaments/resolveIncidence",$scope);
    };

    $scope.resolve = function(team,reportMatch){
        TournamentService.resolveConfrontation(reportMatch.confrontation,$scope.tournament,team);
    };

    subscriber.add("tournament:resolve",(a)=>{
        dialog.closeAll();
        $scope.loadTournament();
        SystemMessages.okmessage("Incidence resolved correctly!")
    })



});