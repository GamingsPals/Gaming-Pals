
app.service("xhr",function($http,MessageSystem,$rootScope) {


    this.get = function (url, sucess,error) {
        $(".loader").show();
        $http.get(url).then(function (data) {
            if (typeof sucess !== "undefined") {
            sucess(data);
        }
                $(".loader").hide();
        },
            function(data) {
                if (typeof error !== "undefined") {
                    error(data);
                }
                $(".loader").hide();
                MessageSystem.errormessage("Something wrong has happened!");
            });
    };

    this.post = function (url, data, sucess,error) {
        $(".loader").show();
        data[$rootScope.csrf.parameterName] = $rootScope.csrf.token;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = data._csrf;
        $http({
            method: 'POST',
            url: url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-CSRF-TOKEN': data._csrf

            },
            transformRequest: function (obj) {
                let str = [];
                for (let p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            },
            data: data
        }).then(
            function(data){
                if(typeof sucess !== "undefined"){
                    sucess(data);
                }
                $(".loader").hide();
            },
            function(data) {
                if (typeof error !== "undefined") {
                    error(data);
                }
                MessageSystem.errormessage("Something wrong has happened!");
                $(".loader").hide();
            }
        );
    };
});

app.service("localization",function(xhr,$cookies){
    this.loc = {};
    this.base_lan = 'en';

    this.init = function($scope){
        let object = this;
        language = this.getLanguage($cookies);

        xhr.get("assets/localitation/"+this.base_lan+".json",function(data){
            Object.assign(object, data.data);
            if (language != object.base_lan) {
                xhr.get("assets/localitation/" + language + ".json", function (data) {
                    Object.assign(object, data.data);
                });
            }
        });
    };

    this.getLanguage = function(cookies){
        return (typeof cookies.get("language")!= 'undefined') ? cookies.get("language") : "en";
    }
});


app.service("SearchService", function(xhr){
    this.search = [];

    this.filter = function(filter,callback){
        let object = this;
        xhr.get("api/search?"+$.param(filter),function(data){
            object.search = data.data;
            if (typeof callback !== "undefined") callback();
        })
    };

    this.findAll = function(){
        let object = this;
        xhr.get("api/search",function(data){
            object.search = data.data;
        })
    };
});

app.service("MainPageService",function(xhr,ActorService){
    this.main = {};

    this.mainData = function(){
        let object = this;
        xhr.get("api/main",function(data){
            object.main = data.data;
            data.data.bestclassified = object.processActors(data.data.bestclassified);
        })
    };

    this.processActors = function (actors) {
        actors.forEach(function(a,e){
            actors[e] = ActorService.processActor(a);
        });

        return actors;
    }

});

app.service("ActorService",function(xhr,auth){

    this.actor = {};
    this.notFound = false;
    this.search = [];
    this.reportedList = {};

    this.UserProfile = function(name){
        let object = this;
        if(typeof name === "undefined"){
            name = this.actor.actor.userAccount.username;
        }
        xhr.get("api/user/"+name,function(data){
            object.actor = data.data;
            object.processActors();
            object.notFound = false;
        },function(data){
            object.notFound = true;
        })
    };


    this.reportedUsers = function(){
        let object = this;
        xhr.get("api/report/user/list" ,function(response){
            object.reportedList = response.data;
        });
    };




    this.rate = function(user,data,sucess,error){
        let object = this;
        xhr.post("api/user/"+user+"/rate", data,sucess,error);
    };

    this.report = function(user,data,sucess,error){
        let object = this;
        xhr.post("api/user/"+user+"/report", data,sucess,error);
    };

    this.followOrUnfollow = function(id,callback){
        let object = this;
        xhr.get("api/user/"+id+"/follow",function(data){
            if (typeof callback!== "undefined") callback(data);
            auth.load(()=>{},true);
        })
    };

    this.processActors = function(){
        let object = this;
        this.actor.actor = this.processActor(this.actor.actor);
        this.actor.followers.forEach(function(a,e){
            object.actor.followers[e] =  object.processActor(a);
        });
        this.actor.following.forEach(function(a,e){
            object.actor.following[e] =  object.processActor(a);
        });
    };

    this.processActor = function(actor){
        actor.avgknowledge = 0;
        actor.avgattitude = 0;
        actor.avgskill = 0;
        let object = this;
        actor.ratingsReceived.forEach(function(a){
            actor.avgknowledge += a.knowledge;
            actor.avgattitude += a.attitude;
            actor.avgskill += a.skill;
        });
        let nRatings = (actor.ratingsReceived.length>0) ? actor.ratingsReceived.length : 1;
        actor.avgknowledge = Math.round((actor.avgknowledge/nRatings) * 100) / 100;
        actor.avgattitude = Math.round((actor.avgattitude/nRatings) * 100) / 100;
        actor.avgskill = Math.round((actor.avgskill/nRatings) * 100) / 100;
        actor.nRatings = actor.ratingsReceived.length;
        actor.avgrating = (actor.avgattitude + actor.avgskill + actor.avgknowledge) / 3;
        return actor;
    };

});



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


app.service("MessageSystem", function($timeout){

    this.color="";
    this.message = "";
    this.show = false;


   this.okmessage = function(message){
       this.color ="bg-green3";
       this.message = `<i class="fa fa-check"></i> ${message}`;
       this.show = true;
       let object = this;
       $timeout(function(){
           object.show = false;
       },2000);
   };

    this.errormessage = function(message){
        this.color ="bg-red3";
        this.message = `<i class="fa fa-close"></i> ${message}`;
        this.show = true;
        let object = this;
        $timeout(function(){
            object.show = false;
        },3000);
    }

});


app.service("dialog", function(ngDialog){

    this.open = function(template,scope,controller) {
        let path = "assets/html/"+template+".html";
        let options = {};
        options.template = path;
        if(typeof scope !== "undefined") options.scope = scope;
        if(typeof controller !== "undefined") options.controller = controller;
        ngDialog.open(options);
    };

    this.closeAll = function(){
        ngDialog.closeAll();
    }
});
app.service("LolApiService",function(xhr){
    this.main = {};


    this.mainData = function(summoner,success,error){
        let object = this;
        xhr.get("api/lol/addsummoner/"+summoner.summoner+"/"+summoner.region+"?key="+summoner.key,function(data){
            if(typeof success !=="undefined")
            success(data);
        },function(data){
            if(typeof error !=="undefined")
                error(data);
        })
    };

});