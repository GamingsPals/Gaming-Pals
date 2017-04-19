app.directive("table",function(){
   return {
       restrict: "E",
       link: function(scope,element,attrs){
               $.fn.dataTable.enum( [ 'BRONZE','SILVER','GOLD','PLATINUM','DIAMOND','MASTER','CHALLENGER' ] );
               setTimeout(function() {  $(element).DataTable({
                   responsive: true
               }
               );
               }, 200);
       }
   }

});