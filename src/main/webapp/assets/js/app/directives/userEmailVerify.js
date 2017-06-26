app.directive('usernameVerify', function($timeout,$http,$q,localization) {
    return {
        restrict: 'A',
        require: '?ngModel',
        link: function(scope, elem, attrs, ngModel) {
            ngModel.$asyncValidators.userExists = function(modelValue) {
                if(modelValue.length<5) return true;
                return $http.get("/api/actor/"+modelValue+"/available?mode=user").then((a)=>{
                    let data = a.data;
                    if(!data.available){
                        return $q.reject(localization.signupform.wrongusername);
                    }
                    return true;
                })

            };

        }

    }
});

app.directive('emailVerify', function($timeout,$http,$q,localization) {
    return {
        restrict: 'A',
        require: '?ngModel',
        link: function(scope, elem, attrs, ngModel) {
            ngModel.$asyncValidators.emailExists = function(modelValue) {
                return $http.get("/api/actor/"+modelValue+"/available?mode=email").then((a)=>{
                    let data = a.data;
                    if(!data.available){
                        return $q.reject(localization.signupform.wrongemail);
                    }
                    return true;
                })

            };

        }

    }
});