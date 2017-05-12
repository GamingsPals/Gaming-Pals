app.service("AdminService",function(xhr,SystemMessages){
    this.pepe = "lol";
    this.ban = function(user){
        xhr.get("api/admin/ban/"+user.id,(a)=>{
            let banned = (user.userAccount.locked==true) ? "Unbanned" : "Banned";
            SystemMessages.okmessage("User "+banned)
        });
    }
});