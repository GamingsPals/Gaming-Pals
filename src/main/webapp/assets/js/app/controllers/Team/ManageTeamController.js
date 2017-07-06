app.controller("ManageTeamController",function($scope,auth,middleware,$routeParams,xhr,SystemMessages,
                                               TeamService,UserService,SweetAlert,$location,Alerts, localization){
    $scope.team = $scope.$parent.team;
    $scope.inviteForm = {};
    $scope.inviteForm.members = [];
    $scope.selectedmembers = [];
    $scope.formEdit = {"name":$scope.team.name,"picture":$scope.team.picture,"header":$scope.team.header};

    UserService.addCallback((a)=>{
        $scope.allusers = a.slice();
    });
    UserService.findAll();

    $scope.addMember = function(a){
        $scope.selectedmembers.push(a);
        $scope.inviteForm.members.push(a.id);
        $scope.allusers.splice($scope.allusers.indexOf(a),1);
        $scope.showAutoComplete = false;
    };

    $scope.inviteMembers = function(form){
        let data = {"users": form.members};
        let datamESSAGE = {title: localization.confirmTeam.invite,text: localization.confirmTeam.confirmInvite,
            confirmtext:localization.confirmTeam.inviteSend,confirmtitle:localization.confirmTeam.sended};
        datamESSAGE.callback = (a)=>{
            TeamService.invite($scope.team,data,(a)=>{
                $scope.loadTeam($scope.team.id);
            });
        };
        Alerts.confirm(datamESSAGE);
    };

    $scope.delete = function(){
        let data = {title: localization.confirmTeam.delete,text:localization.confirmTeam.deleteConfirm,
        confirmtext:localization.confirmTeam.deleteSend,confirmtitle:localization.confirmTeam.deleted};
        data.callback= (a)=>{
            xhr.get(`api/team/${$scope.team.id}/delete`,(data2)=>{
                SystemMessages.okmessage(localization.confirmTeam.deleted);
                $location.path("/");
            })
        };
        Alerts.confirm(data);

    };

    $scope.edit = function(form){
        let data = form;
        xhr.post(`api/team/${$scope.team.id}/edit`,data,(data2)=>{
            $location.path("team/"+$scope.team.id);
            SystemMessages.okmessage(localization.confirmTeam.edited);
        })
    };
    $scope.leaveTeam = function(actor){
        let data = {title: localization.confirmTeam.leave,text: localization.confirmTeam.leaveConfirm,confirmtitle:localization.confirmTeam.left,
        confirmtext: ""};
        data.callback =(a)=>{
            xhr.get(`api/team/${$scope.team.id}/leave`,(data2)=>{
                $location.path("team/"+$scope.team.id);
                SystemMessages.okmessage(localization.confirmTeam.left);
            })
        };
        Alerts.confirm(data);

    };

    $scope.promoteNewLeader = function(form){
        let data2 = {title: localization.confirmTeam.promote,text: localization.confirmTeam.promoteConfirm,
            confirmtext: localization.confirmTeam.promoted,confirmtitle:localization.confirmTeam.newLeader};
        data2.callback = (a)=>{
            TeamService.promoteNewLeader($scope.team,form.user,()=>{
                $scope.loadTeam($scope.team.id);
            });
        };
        Alerts.confirm(data2);
    };

    $scope.kickMember = function(form){
        let data2 = {title: localization.confirmTeam.kick,text: localization.confirmTeam.kickConfirm,
            confirmtext: localization.confirmTeam.kicked,confirmtitle:localization.confirmTeam.kicked};
        data2.callback = (a)=>{
            TeamService.kickMember($scope.team,form.member);
            $scope.loadTeam($scope.team.id);
        };

        Alerts.confirm(data2);
    };

    $scope.passwordInfo = function(){
        SweetAlert.swal($scope.loc.whatispassword);
    };
    $scope.removeMember = function(a){
        let key = $scope.selectedmembers.indexOf(a);
        let key2 = $scope.inviteForm.members.indexOf(a);
        $scope.selectedmembers.splice(key,1);
        $scope.inviteForm.members.splice(key2,1);
        $scope.allusers.push(a);
        $scope.showAutoComplete = false;
    };



    $scope.getOtherMembers = (actor)=>{
        if(typeof $scope.team==="undefined") return [];
        return $scope.team.users.filter((a)=>{
            return a.id!==actor.id;
        })
    };

});