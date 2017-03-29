app.directive("notPrincipal", function(auth,ActorService){
    return{
        scope: {
            "notPrincipal": "="
        },
        restrict: "A",
        link: function(scope,element,attrs){
            scope.$watch("notPrincipal",function(e){
                notPrincipal(element,scope.notPrincipal,auth);
            });
        }
    }

});

function notPrincipal(element,actor,auth){
    if (auth.isPrincipal(actor)){
        $(element).hide();
    }else{
        $(element).show();
    }
}