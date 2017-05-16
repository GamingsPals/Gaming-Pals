app.service("AdminService",function(xhr,Alerts,SystemMessages,localization){
    this.hooksBannedUsers = [];

    this.addHookBannedUser = function(callback){
        if(typeof callback==="function"){
            this.hooksBannedUsers.push(callback);
        }
    };

    this.ban = function(user){
        let data = {};
        let toban = (user.userAccount.locked==true) ? localization.admin.bans.unban : localization.admin.bans.ban;
        let banned = (user.userAccount.locked==true) ? localization.admin.bans.unbanned : localization.admin.bans.banned;
        let args = {"user": user.userAccount.username,"toban":toban,"banned":banned};
        data.title = localization.eval(localization.admin.bans.suretoban,args);
        data.confirmtitle = banned;
        data.confirmtext = localization.eval(localization.admin.bans.hasbeenbanned,args);
        let object = this;
        data.callback = ()=>{
            xhr.get("api/admin/ban/"+user.id,()=>{
                SystemMessages.okmessage( data.confirmtext);
                closeDropdowns();
                user.userAccount.locked = !user.userAccount.locked;
                object.hooksBannedUsers.forEach((i)=>{
                    if(typeof i==="function"){
                        i(user);
                    }
                });
            });
        };
        Alerts.confirm(data)

    };

    this.deleteReport = function(report,callback){
        let object = this;
        let data = {};
        data.title = localization.admin.reports.suretodelete;
        data.confirmtitle = localization.deleted+"!";
        data.confirmtext = localization.admin.reports.deleted;
        data.callback = (a)=>{
            xhr.get("api/admin/report/"+report.id+"/delete",(a)=>{
                SystemMessages.okmessage(localization.admin.reports.deleted);
                if(typeof callback!=="undefined"){
                    callback(a.response);
                }
            });
        };
        Alerts.confirm(data);

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

function closeDropdowns (){
    let list = $(".dropdown");
    list.each((a)=>{
        $($(list[a]).children("ul")[0]).hide();
        $(".toverlay").remove();
    })
}