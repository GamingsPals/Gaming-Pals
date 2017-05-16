app.service("ActorService",function(xhr,auth){

    this.actor = {};
    this.notFound = false;
    this.search = [];
    this.reportedList = {};

    this.UserProfile = function(name,callback){
        let object = this;
        if(typeof name === "undefined"){
            name = this.actor.actor.userAccount.username;
        }
        if(typeof name==="undefined") return false;
        xhr.get("api/user/"+name,function(data){
            object.actor = data.data;
            object.processActors();
            object.notFound = false;
            if(typeof callback!=="undefined"){
                callback(data.data);
            }
        },function(data){
            object.notFound = true;
        });
    };



    this.findAll = function(callback){
        let object = this;
        if(typeof this.allactors==="undefined"){
        xhr.get("api/actor/all", function(response){
            object.allactors = response.data;
            object.removePrincipal(object.allactors);
            if(typeof callback!=="undefined"){
                callback(response.data);

            }
        })
        }else{
            if(typeof callback!=="undefined"){
                callback(this.allactors);

            }
        }
    };

    this.removePrincipal = function(a){
        let principal = a.find((b)=>{
            return auth.isPrincipal(b);
        });
        if(typeof principal!=="undefined"){
            let key = this.allactors.indexOf(principal);
            this.allactors.splice(key,1);
        }
    };

    this.rate = function(user,data,sucess,error){
        let object = this;
        xhr.post("api/user/"+user+"/rate", data,sucess,error);
    };

    this.report = function(user,data,sucess,error){
        let object = this;
        xhr.post("api/user/report/"+user, data,sucess,error);
    };

    this.followOrUnfollow = function(id,callback){
        let object = this;
        xhr.get("api/user/"+id+"/follow",function(data){
            if (typeof callback!== "undefined")
                callback(data);
            auth.load(()=>{},true);
        })
    };


    this.team = function(data,sucess,error){
        let object = this;
        xhr.post("team/user/create",data,sucess,error);
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




    this.getOneActor = function(name,callback){
        let object = this;
        xhr.get("api/actor/"+name ,function(response){
            if(typeof callback !=="undefined"){
                callback(response.data);
            }
        });
    };
    
    
    this.getUsers = function(teamId){
        let object = this;
        xhr.get("api/users/list?teamId="+teamId ,function(response){
            object.users = response.data;
        });
    };

});