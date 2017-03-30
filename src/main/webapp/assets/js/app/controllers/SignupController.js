app.controller('SignupController', function($scope, middleware, xhr, $location) {

	$scope.enviarForm = function() {
		xhr.post("api/signup", $scope.signupform);
		$location.path("/");
	}
});
