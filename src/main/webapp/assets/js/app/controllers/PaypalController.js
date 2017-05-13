app.controller('PaypalController', function($scope, middleware, xhr, $location) {
	middleware.needRol("USER");
	$scope.ejecutaPaypal = function() {
		xhr.get("api/user/updatepaypal");
		$location.path("/");
	}
	$scope.ejecutaPaypal();
});
