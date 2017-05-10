let paginations = [];
app.directive("paginate",function($filter){
    return{
        restrict: "AEC",
        scope:{
            "paginate": "=",
        },
        link: function(scope,element,attrs){

            scope.watch("paginate",(a)=>{
                if(a){
                   let pageResults = attrs.limit;
                   let page = attrs.page;
                }
            })
        }
    }
});