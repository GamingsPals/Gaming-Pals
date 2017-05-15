app.directive("tooltip",function(){
    return {
        scope: {
            "tooltip": "@"
        },
        restrict:"A",
        link: function(scope,element,attrs){

                let tooltip = $($(element).children(".tooltip")[0]);
                let link = $($(element).children(".open-tooltip")[0]);
                $(element).css({"position":"relative"});
                let id = Math.floor(Math.random()*(100));
                $(tooltip).attr("data-tooltip",id);
                let showTooltip = function(){
                    tooltip.show();
                };
                let hideTooltip = function(){
                    tooltip.hide();
                };
                $(link).on("mousemove",(a)=>{
                    tooltip.css({"top":0,"left":0,"z-index":"15","background-color":"white"});
                    showTooltip();
                });
                $(element).on("mouseleave",(a)=>{
                    hideTooltip();
                });
                $(tooltip).on("mouseleave",(a)=>{
                    hideTooltip();
                });




        }
    }

});