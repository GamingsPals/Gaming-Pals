
app.service("auth", function(xhr){

    this.principal = {};
    this.listeners = [];

    this.load = function(callback,force){
        let object = this;
        if (Object.keys(object.principal).length==0 || force==true) {
            xhr.get("api/auth/isauthenticated", function (data) {
                object.principal = data.data;
                object.callListeners(data.data);
                if(typeof  callback!=="undefined"){
                    callback();
                }
            })
        }else{
            callback();
            this.callListeners(this.principal);
        }
    };

    this.callListeners = function(data){
        this.listeners.forEach((a)=>{
            if(typeof a==="function"){
                a(data);
            }
        })
    };

    this.addListener = function(callback){
        this.listeners.push(callback);
    };

    this.isPrincipal = function(actor){
        if (typeof actor === "undefined" || !this.isLoaded() || !this.isAuthenticated()) return false;
        let result = this.principal.actor.id == actor.id;

        return result;
    };

    this.isPrincipalFollowing = function(actor){
        if(typeof actor==="undefined" || typeof this.principal.actor==="undefined" || this.hasRole('ADMIN')){
            return false;
        }
        return typeof this.principal.following.find((a)=> {return a.id == actor.id}) !== "undefined";
    };

    this.isLoaded = function(){
        return !Object.keys(this.principal).length==0;
    };

    this.isAuthenticated = function(){
        if (!this.isLoaded()) return false;

        return this.principal.authenticated;
    };

    this.hasRole = function(rol){
        let result = false;
        if (!this.isLoaded() || !("roles" in this.principal)) return false;
        let rolesAuthority = this.principal.roles;
        rolesAuthority.forEach(function(b){
            if (b.authority.toLowerCase() == rol.toLowerCase()){
                result = true;
            }
        });
        return result;
    }

});