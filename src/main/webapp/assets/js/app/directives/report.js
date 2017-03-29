app.directive("report", function(dialog){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
            $(element).addClass("cursor-pointer").addClass("red3");
        }
    }
});