app.controller('SignupController', function($scope, middleware, xhr, $location,LanguageService, dialog) {
    middleware.needRol("NONE");
    $scope.success = false;
    $scope.languagesForn = [];
    LanguageService.getAll(function(data){
		$scope.languagesForn = data;
	});

	$scope.enviarForm = function() {
		xhr.post("api/signup", $scope.form,function(){
            dialog.closeAll();
            $location.path("/login");
        },function(){
		    $scope.error = "There was something wrong with your form, try again!"
            dialog.closeAll();
        });

	}
});
