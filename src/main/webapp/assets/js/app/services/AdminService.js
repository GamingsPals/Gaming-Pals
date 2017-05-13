app.service("AdminService",function(xhr,SystemMessages){
    this.hooksBannedUsers = [];

    this.addHookBannedUser = function(callback){
        if(typeof callback==="function"){
            this.hooksBannedUsers.push(callback);
        }
    };

    this.ban = function(user){
        let object = this;
        xhr.get("api/admin/ban/"+user.id,(a)=>{
            let banned = (user.userAccount.locked==true) ? "Unbanned" : "Banned";
            SystemMessages.okmessage("User "+banned);
            object.closeDropdowns();
            user.userAccount.locked = !user.userAccount.locked;
            object.hooksBannedUsers.forEach((i)=>{
                if(typeof i==="function"){
                    i(user);
                }
            });
        });
    };

    this.deleteReport = function(report,callback){
        let object = this;
        xhr.get("api/admin/report/"+report.id+"/delete",(a)=>{
            SystemMessages.okmessage("Report removed");
            if(typeof callback!=="undefined"){
                callback(a.response);
            }
        });
    };

    this.closeDropdowns = function(){
        let list = $(".dropdown");
        list.each((a)=>{
            $($(list[a]).children("ul")[0]).hide();
            $(".toverlay").remove();
        })
    };

    this.getBannedUsers = function (callback) {
        xhr.get("api/admin/ban/list",(a)=> {
            if (typeof callback !== "undefined") {
                callback(a.data);
            }
        })
    };
    this.getReportedUsers = function(callback) {
        let object = this;
        xhr.get("api/admin/reports/list", function (response) {
            if (typeof callback !== "undefined") {
                callback(response.data);
            }
        });
    }
});