app.directive("markdown", function(){
    return {
        restrict: 'A',
        scope: {
            "markdown": "="
        },
        link: function(scope, element, attrs) {
            let converter = new showdown.Converter();
            element.addClass("markdown");
            scope.$watch("markdown",(a)=>{
                let htmlText = converter.makeHtml(scope.markdown);
                element.html(htmlText);
            })

        }
    }
});