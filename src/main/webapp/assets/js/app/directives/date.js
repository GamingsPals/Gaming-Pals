

app.directive("input",function(localization,$compile){
    return {
        restrict: "E",
        link: function(scope,element,attrs){
            if(attrs.type==="date"){
                $(element).attr("placeholder", "yyyy-mm-dd");
                $(element).after(`<span class='small'>${localization.datetimeexample} 2017-07-21</span>`);
            }
            if(attrs.type==="datetime-local"){
                $(element).attr("placeholder", "yyyy-mm-ddTHH:MM:SS");
                $(element).after(`<span class='small'>${localization.datetimeexample} 2017-07-21T23:00:00</span>`);
            }
        }
    }
});
