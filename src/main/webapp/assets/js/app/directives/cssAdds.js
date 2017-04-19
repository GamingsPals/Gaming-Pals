app.directive("button",function(){
   return{
       restrict:"C",
       link: function(scope,element,attrs){
           $(element).css("position","relative").addClass("hvr-radial-in");
           $("input[type=submit]").css("position","relative").addClass("hvr-radial-in");
       }
   }

});

