app.service("subscriber",function(){
    this.subscribers = {};

    this.add = function (name,callback){
        if(typeof callback!=="function") return false;
            this.create(name);
            let exists = this.subscribers[name].callbacks.find((a)=>{
                return a.toString() === callback.toString();
            });
            if(typeof exists ==="undefined"){
            this.subscribers[name].callbacks.push(callback);
        }
    };

    this.create = function(name){
        if(typeof this.subscribers[name]==="undefined") {
            this.subscribers[name] = {};
            this.subscribers[name].callbacks = [];
            this.subscribers[name].data = {};
        }

    };


    this.get = function(name){
        if(typeof this.subscribers[name]==="undefined"){
            return this.subscribers[name].data;
        }
        return false;
    };

    this.set = function(name,data){
        this.create(name);
        this.subscribers[name].data = data;
        this.subscribers[name].callbacks.forEach((a)=>{
            a(data);
        })

    }


});