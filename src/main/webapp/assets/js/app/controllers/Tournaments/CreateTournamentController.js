app.controller('CreateTournamentController', function($scope, xhr, $location,middleware,dialog,TournamentService,localization) {
    middleware.needRol("ADMIN");
	$scope.enviarTournamentForm = function(data) {
		xhr.post("api/createTournament",data, function(){
            $location.path("/tournament/list");
		},(a)=>{
			$scope.error = localization.error;
		});
	}
});
