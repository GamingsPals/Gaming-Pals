let app =
    angular.module('App',['ngRoute','ngSanitize','ngRoute','ngCookies','ngDialog'])
        .config(function($routeProvider,$locationProvider){
            $routeProvider.when("/", {
                templateUrl : "assets/html/main.html",
                controller: "HomeController"
            })
                .when("/login", {
                    templateUrl : "assets/html/login.html",
                    controller: "LoginController"
                })
                .when("/profile/:username", {
                    templateUrl : "assets/html/profile.html",
                    controller: "ProfileController"
                }).when("/search", {
                templateUrl : "assets/html/search.html",
                controller: "SearchController"
            })
                .otherwise({
                    redirectTo: '/'

                });
            $locationProvider.html5Mode(true);
        });


