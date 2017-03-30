
app.directive("item",function(LoLStaticData){
    return {
        restrict: "AEC",
        scope:{
            itemid: "="
        },
        link: function(scope,element,attrs){
            scope.$watch(function(d,v){
                console.log(scope);
                $(element).html(`<img src="${LoLStaticData.getItemIcon(scope.itemid)}" width="40" />`);
            })
        }
    }
});