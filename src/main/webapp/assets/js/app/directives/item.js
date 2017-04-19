
app.directive("item",function(LoLStaticData){
    return {
        restrict: "AEC",
        scope:{
            itemid: "=",
            itemwidth: "="
        },
        link: function(scope,element,attrs){
            scope.$watch(function(d,v){
                let width = (typeof scope.itemwidth !== "undefined") ? scope.itemwidth : "40";
                if (scope.itemid!=0){
                $(element).html(`<img src="${LoLStaticData.getItemIcon(scope.itemid)}" width="${width}" />`);
                }
            })
        }
    }
});