app.directive("stored", function(){
    return{
        scope: {
            "stored": "="
        },
        restrict: "A",
        link : function(scope,element,attrs){
            let changed = false;
            scope.$watch("stored",function(a){
                if(!changed){
                    console.log(scope);
                }

            });
        }
    }
});