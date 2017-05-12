app.config(function($routeProvider,$locationProvider){
    routes.forEach(function(a){
        if(typeof a.options.templateUrl !=="undefined"){
            a.options.templateUrl = `assets/html/${a.options.templateUrl}.html`;
        }
        if(typeof a.options.controller !=="undefined"){
            a.options.controller = `${a.options.controller}Controller`;
        }
        $routeProvider.when(a.route,a.options);
    });

    $routeProvider.otherwise({
        redirectTo: '/'
    }, {reloadOnSearch: false});

    $locationProvider.html5Mode(true);
});