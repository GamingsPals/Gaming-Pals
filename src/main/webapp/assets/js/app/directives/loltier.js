
app.directive("loltier",function(){
    return {
        restrict: "AEC",
        link: function(scope,element,attrs){
            let assetsPath = `assets/images/games/lol/tiers/`;
            let image = $(`<img class="tier-icon" src="${assetsPath}${attrs.loltier.toLowerCase()}.png" />`);
            $(element).html(image);
            scope.$watch(function(){
                $(element).html(image);
            })
        }
    }
});