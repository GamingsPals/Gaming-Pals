app.controller('EditProfileController', function($scope, middleware, xhr, $location,LanguageService,dialog,auth) {
    middleware.needRol("USER");
    $scope.languagesForm = [];
    LanguageService.getAll(function(data){
		$scope.languagesForm = data;
	});
    if(auth.isAuthenticated()){
        $scope.form = auth.principal.actor;
        $scope.form.username = $scope.form.userAccount.username;
    }
	$scope.enviarForm = function() {
        $scope.form.languages.forEach((value,key,arr)=>{
            $scope.form.languages[key] = value.id;
        });
	    xhr.post("api/user/edit", $scope.form,function(){
		dialog.closeAll();
		auth.load(()=>{},true);
        },function(){
		    $scope.error = "There was something wrong with your form, try again!"
        });

	};
});
