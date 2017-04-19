
app.controller('LoginController',function($scope,dialog,middleware,$location,auth){
    middleware.needRol("NONE");
    $scope.error = $location.search().error;
    dialog.open("login",$scope);
    $scope.goLogin = function(){
        dialog.closeAll();
        dialog.open("signup");
    }

});
