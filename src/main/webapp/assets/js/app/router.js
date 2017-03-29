app.config(function($routeProvider,$locationProvider){
    routes.forEach(function(a){
        a.options.templateUrl = `assets/html/${a.options.templateUrl}.html`;
        a.options.controller = `${a.options.controller}Controller`;
        $routeProvider.when(a.route,a.options);
    });

    $routeProvider.otherwise({
        redirectTo: '/'
    });

    $locationProvider.html5Mode(true);
});