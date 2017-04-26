app.service("middleware",function(auth,$location){

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
            console.log(rol);
            if ((!auth.hasRole(rol) || rol.toLowerCase() == "NONE".toLowerCase()) && !result){
                return object.goTo('');
            }
        });
    };


    this.goTo = function(path){
        $location.path(path);
        return true;
    }

});