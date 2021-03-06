app.service("UserService",function(xhr,auth){

    this.callbacks = [];

    this.findAll = function(callback){
        if(auth.isAuthenticated()){
        let object = this;
        if(typeof this.alluser!=="undefined"){
            object.callbacks.forEach((a)=>{
                a(object.alluser);
            });
            if(typeof callback!=="undefined")
                callback(this.alluser);
            return false;
        }
        xhr.get("api/user/all", function(response){
            object.alluser = response.data;
            object.removePrincipal(object.alluser);
            if(typeof callback!=="undefined"){
                callback(response.data);
            }
            object.callbacks.forEach((a)=>{
                a(object.alluser);
            })
        })
        }
    };


    this.addCallback = function(a){
        if(typeof a === "function")
        this.callbacks.push(a);
    };

    this.removePrincipal = function(a){
        let principal = a.find((b)=>{
            return auth.isPrincipal(b);
        });
        if(typeof principal!=="undefined"){
           let key = this.alluser.indexOf(principal);
           this.alluser.splice(key,1);
        }
    }


});