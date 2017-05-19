String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

$(document).ready(function(){

    let menu = $(".nav-horizontal");

    let width = $(window).width();

    if(width<1025){
        menu.removeClass("nav-horizontal").addClass("nav-menu");
    }

    $(".nav-button").on("click",function(e){
        e.preventDefault();
        openMenu();
    });
    $("body").on("click",".overlay",function(e){
        e.preventDefault();
        closeMenu();
    });
    $(window).on("scroll",function(){
        //changeNavMenuToFixed()
    });

    //changeNavMenuToFixed();

    loadSlider();

});

function changeNavMenuToFixed(){
    if ($(window).scrollTop() > 37) {
        $('.header').addClass("fixed-menu");
    } else {
        $('.header').removeClass("fixed-menu");
    }
}

function openMenu(){
    let overlay = $("<div class='overlay'></div>");
    let menu = $(".nav-menu").children("ul");
    $("body").append(overlay).css("overflow","hidden");
    $(".header").css("z-index",10);
    menu.addClass("open");
}

function closeMenu(){
    $(".header").css("z-index",2);
    $(".nav-menu").children("ul").removeClass("open");
    $("body").css("overflow","auto");
    $(".overlay").remove();
}

function loadSlider(){
    $('.flexslider').flexslider({
        animation: "slide"
    });

}

