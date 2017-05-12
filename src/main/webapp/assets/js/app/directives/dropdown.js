app.directive("dropdown",function(){
    return {
        restrict: "AEC",
        link: function(scope,element,attrs){
            let elipsis = $($(element).children(".dropdown-button")[0]);
            let list = $($(element).children("ul")[0]);
            console.log(list);
            let overlay = $(`<div class="toverlay"></div>`);
            elipsis.on("click",function(e){
                e.preventDefault();
                $(".toverlay").hide(150);
                $("body").append(overlay);
                list.show();
                overlay.on("click",function(e){
                    e.preventDefault();
                    list.hide(150);
                    $(this).remove();
                })
            });

        }
    }
});