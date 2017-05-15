app.service("TeamService", function(xhr){

   this.get = function(name,callback){
       xhr.get("api/team/"+name,(a)=>{
            if(typeof callback!=="undefined"){
                callback(a);
            }
       });
   }

});