app.controller('SignupController', function($scope, middleware, xhr) {

	$scope.enviarForm = function() {
		xhr.post("api/signup", $scope.signupform);
	}
});
