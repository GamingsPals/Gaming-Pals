
app.directive("select",function(){
    return{
        restrict: "E",
        link: function(scope,element,attrs){
            $(element).selectric(
                {
                    responsive: true,
                    disableOnMobile: true
                }
            );
        }
    }
});