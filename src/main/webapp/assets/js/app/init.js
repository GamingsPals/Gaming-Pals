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
                }).
                when("/user/reported/list", {
                    templateUrl: "assets/html/viewreport.html",
                    controller: "ReportedUserListController"
                })
                .when("/profile/:username", {
                    templateUrl : "assets/html/profile.html",
                    controller: "ProfileController"
                }).when("/search", {
                templateUrl : "assets/html/search.html",
                controller: "SearchController"
            }).when("/lol/user/:name/:region", {
                templateUrl : "assets/html/lolApi.html",
                controller: "LolApiController"
            }).when("/lol/user/", {
                templateUrl : "assets/html/lolApi.html",
                controller: "LolApiController"
            }).when("/lol/vinculated/:idSummoner/:region", {
                templateUrl : "assets/html/lolApiVinculated.html",
                controller: "LolApiVinculatedController"
            })   .otherwise({
                    redirectTo: '/'

                });
            $locationProvider.html5Mode(true);
        });

