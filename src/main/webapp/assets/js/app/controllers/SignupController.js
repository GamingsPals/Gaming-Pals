app.controller('SignupController', function($scope, middleware, xhr, $location,LanguageService) {
    middleware.needRol("NONE");
    $scope.success = false;
    $scope.languagesForn = [];
    LanguageService.getAll(function(data){
		$scope.languagesForn = data;
	});

	$scope.enviarForm = function() {
	    console.log($scope.form);
		xhr.post("api/signup", $scope.form,function(){
            $location.path("/login");
        },function(){
		    $scope.error = "There was something wrong with your form, try again!"
        });

	}
});
