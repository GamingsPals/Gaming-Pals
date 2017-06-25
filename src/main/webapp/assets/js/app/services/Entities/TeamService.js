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
   };

   this.invite = function(team,data,success,error){
       xhr.post(`api/invitations/${team.id}/new`,data,success,error);
   };


    this.promoteNewLeader = function(team,user,succes,error){
        xhr.get(`api/team/${team.id}/leader/${user}`,succes,error);
    };

   this.kickMember = function(team,user,succes,error){
       xhr.get(`api/team/${team.id}/kick/${user}`,succes,error);
   }

});