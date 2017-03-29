app.directive("flag", function($compile){
    return {
        restrict: "AE",
        link: function(scope,element,attrs){
            $(element).html(`<img class="flag" ng-src="assets/images/flags/${attrs.lang}.png" /></li>`);
            $compile(element.contents())(scope);
        }
    }
});