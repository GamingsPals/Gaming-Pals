
app.service("auth", function(xhr){

    this.principal = {};
    this.load = function(callback,force){
        let object = this;
        if (Object.keys(object.principal).length==0 || force==true) {
            xhr.get("api/isauthenticated", function (data) {
                object.principal = data.data;
                callback();
            })
        }else{
            callback();
        }
    };

    this.isPrincipal = function(actor){
        if (typeof actor === "undefined" || !this.isLoaded() || !this.isAuthenticated()) return false;
        return this.principal.actor.id == actor.id;
    };

    this.isPrincipalFollowing = function(actor){
        if(typeof actor==="undefined" || typeof this.principal.actor==="undefined"){
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