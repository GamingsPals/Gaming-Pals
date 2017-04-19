
app.directive("select",function(){
    return{
        restrict: "E",
        link: function(scope,element,attrs){
            let selectric = $(element).selectric(
                {
                    responsive: true,
                    disableOnMobile: true
                }
            );
            scope.$watch(function(){
                $(element).data('selectric').refresh();
            })
        }
    }
});