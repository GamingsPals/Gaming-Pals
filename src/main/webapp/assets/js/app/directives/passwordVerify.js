app.directive('passwordVerify', function() {
    return {
        restrict: 'A',
        require: '?ngModel',
        link: function(scope, elem, attrs, ngModel) {
            ngModel.$validators.passwordVerifyValidator = function(modelValue, viewValue) {
                return viewValue === attrs.passwordVerify;
            };
        }
    };
});