app.controller('EditProfileController', function($scope, middleware, xhr, $location,LanguageService,dialog,auth) {
    middleware.needRol("USER");
    $scope.languagesForm = [];
    LanguageService.getAll(function(data){
		$scope.languagesForm = data;
	});
    let dialog2 = dialog.open("profile/editProfile",$scope);
    dialog.redirect(dialog2,(a)=>{

    });
    if(auth.isAuthenticated()){
        $scope.form = auth.principal.actor;
        $scope.form.username = $scope.form.userAccount.username;
    }
	$scope.enviarForm = function(data) {
        data.languages.forEach((value,key,arr)=>{
            data.languages[key] = value.id;
        });
	    xhr.post("api/user/edit", data,function(){
		dialog.close(dialog2);
		auth.load(()=>{},true);
        },function(){
		    $scope.error = "There was something wrong with your form, try again!"
        });

	};
});
