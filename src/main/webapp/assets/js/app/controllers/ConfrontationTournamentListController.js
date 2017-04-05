app.controller('ConfrontationTournamentListController', function($scope, TournamentService, dialog) {
	$scope.As = TournamentService;
	$scope.As.getConfrontations($scope.$parent.$parent.tournmanentId);

    $scope.reportMatch = function(confrontationId){
        $scope.confrontationId = confrontationId;
        dialog.open("reportMatch",$scope);
    }
});
