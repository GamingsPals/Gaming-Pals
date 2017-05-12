app.service("AdminService",function(xhr,auth){
    this.pepe = "lol";
    this.ban = function(id){
        xhr.get("api/admin/ban/"+id);
    }
});