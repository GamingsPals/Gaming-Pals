app.controller('SignupController',function($scope,middleware, xhr){


    $scope.enviarForm = function(){
        xhr.post("api/test",$scope.signupform);
    }
});