
app.controller('LoginController',function($scope,dialog,middleware,$location,auth ){
    middleware.needRol("NONE");
    $scope.error = $location.search().error;

    let dialog2 = dialog.open("auth/login",$scope);
     dialog.redirect(dialog2,(a)=>{

     });

});
