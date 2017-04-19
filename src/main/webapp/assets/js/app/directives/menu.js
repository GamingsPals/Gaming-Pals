app.directive("menu",function(){
    return {
        restrict: "AEC",
        link: function(scope,element,attrs){
            let elipsis = $($(".profile-nav-mbutton").children("a")[0]);
            let list = $($(element).children("ul")[0]);
            let overlay = $(`<div class="toverlay"></div>`);
            console.log("hola");
            elipsis.on("click",function(e){
                e.preventDefault();
                $(".toverlay").hide();
                $("body").append(overlay);
                list.show();
                overlay.on("click",function(e){
                    e.preventDefault();
                    list.hide();
                    $(this).remove();
                })
            });

        }
    }
});