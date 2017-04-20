app.service("socket", function(auth,$rootScope){

    this.socket = null;
    this.messages = [];
    this.usersConnected = [];
    this.connected = false;
    this.listeners = [];

    this.init = function(scope){
        if (auth.isAuthenticated()){
        this.scope = scope;
        this.socket = io.connect('http://gaming-pals.com:8081', { 'forceNew': true,
        query: `id=${auth.principal.actor.id}&picture=${auth.principal.actor.picture}&username=${auth.principal.actor.userAccount.username}`});
        this.connected = true;
        this.listen();
        }
    };

    this.listen = function(){
        let o = this;

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
        let o = {"route": route, "callback":callback};
        this.listeners.push(o);
    };

    this.emit = function(route,data){
        this.socket.emit(route,data);
    };

    this.isUserConnected = function(id){
        return this.usersConnected.find((a)=>{
            return a.id == id;
        })
    };
    this.disconnect = function(){
        this.connected = false;
        if(this.socket!==null){
            this.socket.disconnect();
        }

    }
});