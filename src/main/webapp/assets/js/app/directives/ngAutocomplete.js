app.directive("ngAutocomplete", function(ActorService){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
                let index = 0;
                let selectedLi = null;
                $(element).css({position:"relative"});
                let navigateList = function(e){
                    let lis = $(element).parent().children("ul").children("li");
                    let size = lis.length-1;
                    let active = false;
                    $(element).parent().children("ul").show();
                    switch(e.key){
                        case "ArrowDown":
                            index = index+1;
                            active = true;
                            e.preventDefault();
                            break;
                        case "ArrowUp":
                            index = index-1;
                            active = true;
                            e.preventDefault();
                            break;
                        case "Enter":
                            selectedLi.trigger("click");
                            break;
                    }
                    if (active){
                        if(index<0) index = 0;
                        if(index>size) index = size;
                        lis.each((test)=>{
                            console.log(test);
                           $(lis[test]).children("a").removeClass("list-hover");
                        });
                        $(lis[index]).children("a").addClass("list-hover");
                        selectedLi = $(lis[index]).children("a");
                    }
                };
                $(element).on("keydown",function (e) {
                    navigateList(e);
                }).on("blur",function(a){
                    let lis = $(element).parent().children("ul").children("li");
                    $(lis).children("li").each(()=>{
                        $(this).children("a").removeClass("list-hover");
                    });
                    index = 0;
                    let interval = setInterval(()=>{
                        $(this).parent().children("ul").children().hide(300);
                        clearInterval(interval);
                    },300);
                }).on("focus",function (a) {
                    $(this).parent().children("ul").children().show();
                });

        }
    }


});