app.service("DashBoardService",function($location,xhr){

    this.data = {};

    this.getDashboardData  = (callback)=>{
        let object = this;
        console.log(Object.keys(this.data).length>0);
        if(Object.keys(this.data).length>0 && $location.path()!=="/"){
            if(typeof callback!=="undefined") callback(this.data);
            return false;
        }
        xhr.get("api/main",(a)=>{
            object.data = a.data;
            if(typeof callback!=="undefined") callback(a.data);
        })
    }


});