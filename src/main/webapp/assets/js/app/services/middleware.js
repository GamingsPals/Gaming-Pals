app.service("middleware",function(auth,$location,SystemMessages){

    this.needRol = function(rol){
        let object = this;
        auth.load(function(){
            if (!auth.principal.authenticated) {
                if ( rol.toLowerCase() != "NONE".toLowerCase()) {
                    return object.goTo('login');
                }
                return true;
            }
            if (rol.toLowerCase() == "ANY".toLowerCase()) return true;
            let result = false;
            if (rol.indexOf(',')!="-1"){
                let roles =  rol.split(",");
                roles.forEach(function(a){
                    if ((auth.hasRole(a))){
                        result = true;
                        return false;
                    }
                });
            }
            if ((!auth.hasRole(rol) || rol.toLowerCase() == "NONE".toLowerCase()) && !result){
                SystemMessages.errormessage("You don't have permission to access this section");
                return object.goTo('');
            }
        });
    };


    this.goTo = function(path){
        $location.path(path);
        return true;
    }

});