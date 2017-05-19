app.service("DashBoardService",function(xhr){

    this.data = {};

    this.getDashboardData  = (callback)=>{
        let object = this;
        if(Object.keys(this.data).length>0){
            if(typeof callback!=="undefined") callback(this.data);
            return false;
        }
        xhr.get("api/main",(a)=>{
            object.data = a.data;
            if(typeof callback!=="undefined") callback(a.data);
        })
    }


});