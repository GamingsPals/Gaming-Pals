let app =
    angular.module('App',['ngRoute','ngSanitize','ngRoute','ngCookies','ngDialog','ngMessages']);


app.run(function($rootScope,$location,dialog) {
    $rootScope.$on("$locationChangeStart", function(event, next, current) {
        closeMenu();
    });
    let history = [];

    $rootScope.$on('$routeChangeSuccess', function() {
        history.push($location.$$path);
        let top = $("#top");
        if(typeof top !=="undefined"){
            $("html, body").animate({ scrollTop: top.offsetTop }, "medium");
        }
    });

    $rootScope.back = function () {
        let prevUrl = history.length > 1 ? history.splice(-2)[0] : "/";
        $location.path(prevUrl);
        dialog.closeAll();
    };

});