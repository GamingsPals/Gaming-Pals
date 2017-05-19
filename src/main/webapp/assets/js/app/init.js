let app =
    angular.module('App',['ngRoute','ngSanitize','ngRoute','ngCookies','ngDialog','ngMessages','angular-loading-bar',
        'oitozero.ngSweetAlert','luegg.directives']);


app.run(function($rootScope,$location,dialog,$anchorScroll) {
    $rootScope.$on("$locationChangeStart", function(event, next, current) {
        closeMenu();
    });
    let history = [];

    let dialogs = {};
    $rootScope.$on('$routeChangeSuccess', function() {
        history.push($location.$$path);
    });



    $rootScope.back = function () {
        let prevUrl = history.length > 1 ? history.splice(-2)[0] : "/";
        $location.path(prevUrl);

    };

});