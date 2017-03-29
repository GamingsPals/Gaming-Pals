let app =
    angular.module('App',['ngRoute','ngSanitize','ngRoute','ngCookies','ngDialog']);


app.run(function($rootScope) {
    $rootScope.$on("$locationChangeStart", function(event, next, current) {
        closeMenu();
    });
});