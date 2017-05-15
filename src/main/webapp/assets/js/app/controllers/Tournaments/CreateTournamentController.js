app.controller('CreateTournamentController', function($scope, xhr, $location,middleware,dialog,TournamentService,localization) {
    middleware.needRol("ADMIN");
    let dialog2 = dialog.open("tournaments/createTournament",$scope);
    dialog.redirect(dialog2,(a)=>{
    });
	$scope.enviarTournamentForm = function(data) {
		xhr.post("api/createTournament",data, function(){
            dialog.close(dialog2);
            $location.path("/tournament/list");
		},(a)=>{
			$scope.error = localization.error;
		});
	}
});
