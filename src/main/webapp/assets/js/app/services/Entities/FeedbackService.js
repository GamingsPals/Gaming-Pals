app.service("FeedbackService",function(xhr,auth){

    this._all = [];

    this.all = function(success,error){
        let object = this;
        xhr.get("api/feedback/all",(a)=>{
            object._all = a.data;
        },error)
    };

    this.add = function(body,parent,success,error){
        let data = {"body":body};
        if(typeof parent!=="undefined") data.parent = parent;
        xhr.post("api/feedback/add",data,success,error);
    };

    this.like = function(feedback,success,error){
        xhr.get(`/api/feedback/${feedback.id}/true`,success,error);
    };

    this.dislike = function(feedback,success,error){
        xhr.get(`/api/feedback/${feedback.id}/false`,success,error);
    };


});