
app.directive("loltier",function(){
    return {
        restrict: "AEC",
        scope:{
          loltier: "="
        },
        link: function(scope,element,attrs){
            scope.$watch(function(d,v){
                let tier = 'unranked';
                if(typeof scope.loltier!=="undefined"){
                    tier = scope.loltier.toLocaleLowerCase();
                }
            let assetsPath = `assets/images/games/lol/tiers/`;
            let image = $(`<img class="tier-icon" src="${assetsPath}${tier}.png" />`);
            $(element).html(image);
            })
        }
    }
});