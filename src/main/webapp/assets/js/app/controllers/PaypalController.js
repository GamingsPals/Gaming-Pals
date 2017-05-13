app.controller('PaypalController', function($scope, middleware, xhr, $location) {
	middleware.needRol("USER");
	console.log("ey");
	$scope.ejecutaPaypal = function() {
		xhr.get("api/user/updatepaypal");
		$location.path("/");
	}
	$scope.ejecutaPaypal();
});
