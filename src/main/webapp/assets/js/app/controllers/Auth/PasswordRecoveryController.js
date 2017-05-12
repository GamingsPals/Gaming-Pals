app.controller('PasswordRecoveryController', function($scope, middleware, xhr, $location,LanguageService, dialog) {
    //middleware.needRol("NONE");
    $scope.success = false;
    let dialog2 = dialog.open("auth/passwordRecovery",$scope);
    dialog.redirect(dialog2,(a)=>{
    });
	$scope.submitPasswordRecovery = function(form) {
	    console.log(form);
		xhr.post("api/auth/passwordRecovery", form, function(){
            $scope.success = true;
        },function(e){
		    $scope.notfound = false;
		    $scope.interal = true;
		    if(e.status===404){
		        $scope.notfound = true;
            }
            if(e.status===500){
                $scope.internal = true;
            }

        });

	}
});
