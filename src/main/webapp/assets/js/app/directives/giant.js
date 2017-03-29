app.directive("giant",function(){
    return {
        restrict: "E",
        link: function(scope,element,attrs){
            $(element).css("font-size","4em");
        }
    }
});