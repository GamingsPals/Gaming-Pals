
app.directive("profileHeader",function(){
    return {
        restrict: "C",
        link: function(scope,element,attrs){
            let random = Math.floor((Math.random() * 7) + 1);
            let url = `url(assets/images/profile-${random}.jpg)`;
            $(element).css("background-image",url);
        }
    }
});
