

app.directive("dropdown",function(){
    return {
        restrict: "AE",
        link: function(scope,element,attrs){
            let elipsis = $($(element).children(".dropdown-button")[0]);
            let list = $($(element).children("ul")[0]);
            let overlay = $(`<div class="toverlay"></div>`);
            let overlap = false;
            elipsis.on("click",function(e){
                e.preventDefault();
                $(".toverlay").hide(150);
                $("body").append(overlay);
                list.show();
                let width = list.offset().left+list.width();
                let w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
                let h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
                list.css({"left":'0', "right":''});
                if(width>w || overlap){
                    list.css({"right":'0','left':''});
                    overlap = true;
                }
                overlay.on("click",function(e){
                    e.preventDefault();
                    list.hide(150);
                    $(this).remove();
                })
            });

        }
    }
});
