app.directive("hasRole", function(auth){
    return{
        restrict: "A",
        link: function(scope,element,attrs){
            if (!auth.isAuthenticated()){
                $(element).hide();
            }
        }
    }

});