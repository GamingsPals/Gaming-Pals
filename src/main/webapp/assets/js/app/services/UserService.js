app.service("UserService",function(xhr,auth){

    this.findAll = function(callback){
        if(auth.isAuthenticated()){
        let object = this;
        if(typeof this.alluser==="undefined"){
        xhr.get("api/user/all", function(response){
            object.alluser = response.data;
            if(typeof callback!=="undefined"){
                callback(response.data);

            }
        })
        }
        }else{
            callback(this.alluser);
        }
    };


});