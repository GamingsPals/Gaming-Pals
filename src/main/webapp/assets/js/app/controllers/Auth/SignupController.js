app.controller('SignupController', function($scope, middleware, xhr, $location,LanguageService, dialog) {
    middleware.needRol("NONE");
    $scope.success = false;
    $scope.languagesForn = [];
    let dialog2 = dialog.open("auth/signup",$scope);
    dialog.redirect(dialog2,(a)=>{
    });
    LanguageService.getAll(function(data){
		$scope.languagesForn = data;
	});

	$scope.enviarForm = function(data) {
		xhr.post("api/signup", data,function(){
            $scope.success = true;
        },function(){
		    $scope.error = "There was something wrong with your form, try again!";
            dialog.closeAll();
        });

	}
});
