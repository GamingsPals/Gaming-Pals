app.directive('passwordVerify', function() {
    return {
        restrict: 'A',
        require: '?ngModel',
        link: function(scope, elem, attrs, ngModel) {
                    ngModel.$validators.passwordVerifyValidator = function(modelValue, viewValue) {
                        if(typeof attrs.required==="undefined") {
                            if( typeof attrs.passwordVerify !=="string"){
                                return true;
                            }
                            if(attrs.passwordVerify===""){
                                console.log("here2");
                                return true;
                            }
                        }
                        console.log("here3");
                        return viewValue === attrs.passwordVerify;
                    };

                }

        }
});