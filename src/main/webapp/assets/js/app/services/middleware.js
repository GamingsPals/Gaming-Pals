app.service("middleware",function(auth,$location){

    this.needRol = function(rol){
        console.log("ey");
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
            console.log(rol);
            if (rol.indexOf(',')!="-1"){
                let roles =  rol.split(",");
                console.log(roles);
                roles.forEach(function(a){
                    if ((auth.hasRole(rol))){
                        result = true;
                    }
                });
                console.log(result);
            }
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