app.directive("dialog", function(dialog){
    return {
        restrict: "A",
        link: function(scope,element,attrs){
            $(element).on('click',function(e){
                dialog.open(attrs.dialog,scope,attrs.dialogcontroller);
            })
        }
    }
});

app.directive("image", function(dialog){
    return {
        scope: {
            "image": "="
        },
        restrict: "A",
        link: function(scope,element,attrs){
            scope.$watch("image",(a)=>{
                $(element).on('click',function(e) {
                    dialog.open("showImage", scope);
                });
            })
        }
    }

});