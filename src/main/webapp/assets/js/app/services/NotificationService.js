app.service("NotificationService", function(xhr,auth,$rootScope){
    this.notifications = {"Message":[],"Follower":[],"TeamInvitations":[]};
    this.loaded = false;

    this.getNews = function () {
        let object = this;
        if(this.loaded){
            return true;
        }
        xhr.get("api/notifications/news", function (response) {
            object.notifications = response.data;
            object.loaded = true;
        })
    };

    this.readNotification = function(noti,type){
        if(typeof noti !=="undefined" && typeof type !=="undefined"){
        let o = this;
        xhr.get("api/notifications/"+noti.id+"/read",(a)=>{
            let key = o.notifications[type].indexOf(type);
            o.notifications[type].splice(noti,1);
        });
        }
    };

    this.addNotification = function(notification,type){
       this.notifications[type].push(notification);
    };

    this.setReaded = function(sender){
        let receiver = auth.principal.actor;
        let messageNotification = this.findMessageNotification(sender.id,receiver);
        if(messageNotification!==null && typeof messageNotification!=="undefined"){
            messageNotification.numMessages = 0;
            let key = this.notifications.Message.indexOf(messageNotification);
            this.notifications.Message[key] = messageNotification;
        }
    };

    this.updateMessage = function(sender,receiver) {
        if(sender==receiver) return false;
        let messageNotification = this.findMessageNotification(sender,receiver);
        if(messageNotification===null || typeof messageNotification==="undefined"){
            messageNotification = this.createMessageNotification(sender,receiver);
            this.notifications.Message.push(messageNotification);
            return true;
        }
        messageNotification.numMessages +=1;
        let key = this.notifications.Message.indexOf(messageNotification);
        this.notifications.Message[key] = messageNotification;
        console.log(this.notifications.Message);
        $rootScope.$apply();
    };

    this.createMessageNotification = function(sender,receiver){
        let m = {};
        m.id = Math.floor(Math.random() * (50000));
        m.readed = false;
        m.sender = sender;
        m.receiver = receiver;
        m.numMessages = 1;

        return m;
    };

    this.findMessageNotification = function(sender,receiver){
        if(typeof receiver==="object"){
            receiver = receiver.id;
        }

        let result = this.notifications.Message.find((a)=>{
            return a.sender.id == sender && a.receiver.id == receiver;
        });
        return result;
    };

    this.getNumNewMessages = function(){
        let messages = 0;
        this.notifications.Message.forEach((a)=>{
            messages+=a.numMessages;
        });

        return messages;
    };

    this.numberOfNotifications = function(){
      return this.notifications.Follower.length+this.notifications.TeamInvitations.length;
    }
});