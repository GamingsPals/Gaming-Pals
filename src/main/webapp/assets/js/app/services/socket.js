app.service("socket", function(auth,$rootScope,$location){

    this.socket = null;
    this.messages = [];
    this.usersConnected = [];
    this.connected = false;
    this.listeners = [];

    this.init = function(scope){
        let host = $location.host();
        if (auth.isAuthenticated() && host!=="localhost"){
        this.scope = scope;
        this.socket = io.connect('//gaming-pals.com:8081', { 'secure':true, 'forceNew': true,
        query: `id=${auth.principal.actor.id}&picture=${auth.principal.actor.picture}&username=${auth.principal.actor.userAccount.username}`});
        this.connected = true;
        this.listen();
        }
    };

    this.listen = function(){
        let o = this;
        if(this.socket===null) return false;
        this.socket.on('users-connected',function(data){
            o.usersConnected = data;
            $rootScope.$apply();
        });

        this.socket.on("disconnect", function(){
            o.connected = false;
            $rootScope.$apply();
        });
        this.listeners.forEach(function(a){
            o.socket.on(a.route,function(data){
                if(typeof a.callback!=="undefined"){
                    a.callback(data);
                }
            })
        });
    };

    this.on = function(route,callback){
        if(this.socket===null) return false;
        let o = {"route": route, "callback":callback};
        this.listeners.push(o);
    };

    this.emit = function(route,data){
        if(this.socket===null) return false;
        this.socket.emit(route,data);
    };

    this.isUserConnected = function(id){
        if(this.socket===null) return false;
        return this.usersConnected.find((a)=>{
            return a.id == id;
        })
    };
    this.disconnect = function(){
        if(this.socket===null) return false;
        this.connected = false;
        if(this.socket!==null){
            this.socket.disconnect();
        }

    }
});