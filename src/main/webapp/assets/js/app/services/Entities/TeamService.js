app.service("TeamService", function(xhr){

   this.get = function(name,callback){
       xhr.get("api/team/"+name,(a)=>{
            if(typeof callback!=="undefined"){
                callback(a);
            }
       });
   };

   this.getLeader = function(team){
       if(typeof team!=="undefined"){
           let id = team.idLeader;
           if(id===null) return undefined;
           return team.users.find((a) => {
               return a.id === id;
           });
       }else{
           return undefined;
       }
   }

});