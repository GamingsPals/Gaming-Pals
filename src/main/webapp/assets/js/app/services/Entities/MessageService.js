app.service("MessageService",function(xhr,auth){

    this.createMessage = function(data){
        xhr.post("api/message/new",data);
    };


    this.getMessages = function(receiver,callback){
        let sender = auth.principal.actor;
        xhr.get(`api/messages?sender=${sender.id}&receiver=${receiver.id}`,function(data){
            if(typeof callback!=="undefined"){
                callback(data.data);
            }
        })
    };

    this.getRecents = function(callback){

        xhr.get(`api/messages/recent`,function(data){
            if(typeof callback!=="undefined"){
                callback(data.data);
            }
        })
    }
});