pp.controller('PaypalController', function($scope, middleware, xhr, $location, LanguageService, dialog) {
	middleware.needRol("USER");
	$scope.ejecutaPaypal = function(data) {
		xhr.get("api/user/updatepaypal", data);
	}
});
