app.controller('EditProfileController', function($scope, middleware, xhr, $location,LanguageService,dialog,auth,
                                                 SweetAlert,$route,localization) {
    middleware.needRol("USER");
    $scope.languagesForm = [];
    LanguageService.getAll(function(data){
		$scope.languagesForm = data;
	});
    $scope.chargeForms = function(){
        if(auth.isAuthenticated()){
            $scope.form = auth.principal.actor;
            $scope.form.username = $scope.form.userAccount.username;
            $scope.form.age = new Date($scope.form.age);
            console.log($scope.form.languages);
        }
    };
    $scope.chargeForms();

	$scope.enviarForm = function(data) {
	    let result = Object.assign({},data);
        result.languages.forEach((value,key,arr)=>{
            result.languages[key] = value.id;
        });
	    xhr.post("api/user/edit", result,function(){
		auth.load(()=>{
            SweetAlert.swal(localization.confirmProfile.edited, localization.confirmProfile.editedSuccess, localization.confirmProfile.successE);
            $scope.chargeForms();
        },true);
        },function(){
		    $scope.error = localization.confirmProfile.try
        });

	};
});
