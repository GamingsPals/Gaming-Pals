app.service("chat", function(xhr,socket,auth,MessageService,NotificationService,ActorService){

    this.typing = false;

    this.chatMessages = {};

    this.recents = [];

    this.handled = false;

    this.scopes = [];


    this.getMessages = function(callback){
        let o = this;
        let noti = NotificationService.findMessageNotification(o.userselected.id,auth.principal.actor.id);
        NotificationService.readNotification(noti,"Message");
        o.createMessagesIfNotExists(o.userselected.id);
        if (o.chatMessages[o.userselected.id].messages.length>0){
            o.chatMessages[o.userselected.id].newMessages = 0;
            return false;
        }
        MessageService.getMessages(o.userselected,function(data){
            o.chatMessages[o.userselected.id.toString()].messages = data;
            $(".chat-body-chat-messages").animate({ scrollTop:$(document).height() }, "fast");
        });
        if(typeof callback!=="undefined"){
            callback();
        }
    };

    this.getRecents = function(callback){
        let o = this;
        MessageService.getRecents((data)=>{
            o.recents = data;
            data.forEach((a)=>{
                o.createMessagesIfNotExists(a.id);
            });
            if(typeof callback!=="undefined"){
                callback();
            }
        });
    };

    this.sendMessage = function(message){
        let data = {};
        data.receiver = this.userselected.userAccount.username;
        data.message = message;
        data.notification = false;
        data.sender = auth.principal.actor;
        data.text = message;
        data.moment = + new Date();
        data.notification = true;
        this.updateRecents(this.userselected.id);
        if(typeof socket.socket==="undefined" || socket.socket===null ||  !this.isConnected(this.userselected.id)){
            this.chatMessages[this.userselected.id].messages.push(data);
        }
        socket.emit("new-message",data);
        $(".chat-body-chat-messages").animate({ scrollTop:$(document).height() }, "fast");
        MessageService.createMessage(data);
    };

    this.isConnected = function(user){
        return socket.isUserConnected(user);
    };

    this.updateRecents = function(actor){
        let o = this;
        let search = this.recents.find((a)=>{
            return a.id === +actor;
        });
        if(typeof search!=="undefined"){
            let key = this.recents.indexOf(search);
            this.recents.splice(key,1);
            this.recents.unshift(search);
        }else{
        ActorService.findAll((a)=>{
            let aux = a.find((b)=>{
                return b.id===+actor;
            });
            if(typeof aux!=="undefined"){
                o.recents.unshift(aux);
            }
        });
        }
    };

    this.handleMessages = function(callback){
        let o = this;
        socket.on("receive-message",function(data){
            let id = data.sender.id;
            if(data.sender.id == auth.principal.actor.id){
                id = data.receiver.id;
            }
            o.createMessagesIfNotExists(id);
            o.chatMessages[id].messages.push(data);
            if (typeof o.userselected==="undefined" || o.userselected===null){
                NotificationService.updateMessage(data.sender.id,data.receiver.id);
            }

            if(typeof callback !=="undefined"){
                callback();
            }
            o.updateRecents(id);
            o.scopes.forEach((a)=>{
                a.$apply();
            });
            $(".chat-body-chat-messages").animate({ scrollTop:$(document).height()}, "fast");

        });
    };


    this.createMessagesIfNotExists = function(id){
        let o = this;
        if (typeof o.chatMessages[id]==="undefined"){
            o.chatMessages[id] = {};
            o.chatMessages[id].newMessages = 0;
            o.chatMessages[id].messages = [];
            o.chatMessages[id].typing = false;
        }
    };

    this.handleTyping = function(receiver){
        if(this.typing===true){
            let o = this;
            let intervalTyping = setInterval(function(){
                if (o.typing===true){
                    o.sendTyping(true);
                }else{
                    o.typing = false;
                    o.sendTyping(false);
                    clearInterval(intervalTyping);
                }
            },100);
        }
    };

    this.sendTyping = function(typing){
        let data = {};
        data.receiver = this.userselected;
        data.receiver.username = this.userselected.userAccount.username;
        data.typing = typing;
        socket.emit("typing",data);
    };

    this.setTyping = function(typing){
        this.typing = typing;
        this.handleTyping();
    };

    this.typingListener = function(callback){
        let o = this;
        socket.on("istyping",function(data){
            let id = data.sender.id;
            o.createMessagesIfNotExists(id);
            o.chatMessages[id].typing = data.typing;
            o.scopes.forEach((a)=>{
                a.$apply();
            });
            if(typeof callback!=="undefined"){
                callback();
            }
        })
    };

    this.handleListeners = function(callback){

            this.typingListener(callback);
            this.handleMessages(callback);
    }

});