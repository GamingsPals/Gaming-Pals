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