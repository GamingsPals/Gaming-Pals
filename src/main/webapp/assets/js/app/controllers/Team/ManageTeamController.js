app.controller("ManageTeamController",function($scope,auth,middleware,$routeParams,xhr,SystemMessages,
                                               TeamService,UserService,SweetAlert,$location,Alerts){
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
        let datamESSAGE = {title: "Are you sure you want to invite these users to the team?",text:"You won't be able to " +
        "undone it!",
            confirmtext:"Invitation are sended",confirmtitle:"Sended!"};
        datamESSAGE.callback = (a)=>{
            TeamService.invite($scope.team,data,(a)=>{
                $scope.loadTeam($scope.team.id);
            });
        };
        Alerts.confirm(datamESSAGE);
    };

    $scope.delete = function(){
        let data = {title: "Are you sure you want to delete this team?",text:"You won't be able to recover it!",
        confirmtext:"The Team has been deleted!",confirmtitle:"Deleted!"};
        data.callback= (a)=>{
            xhr.get(`api/team/${$scope.team.id}/delete`,(data2)=>{
                SystemMessages.okmessage("Team deleted!");
                $location.path("/");
            })
        };
        Alerts.confirm(data);

    };

    $scope.edit = function(form){
        let data = form;
        xhr.post(`api/team/${$scope.team.id}/edit`,data,(data2)=>{
            $location.path("team/"+$scope.team.id);
            SystemMessages.okmessage("Team edited!");
        })
    };
    $scope.leaveTeam = function(actor){
        let data = {title: "Leave the Team",text: "Are you sure you want to leave this team?",confirmtitle:"You have left the Team",
        confirmtext: ""};
        data.callback =(a)=>{
            xhr.get(`api/team/${$scope.team.id}/leave`,(data2)=>{
                $location.path("team/"+$scope.team.id);
                SystemMessages.okmessage("You have left the Team!");
            })
        };
        Alerts.confirm(data);

    };

    $scope.promoteNewLeader = function(form){
        let data2 = {title: "Promote member",text: `Are you sure you want to promote this member to leader?`,
            confirmtext: "User promoted to Leader!",confirmtitle:"New leader!!"};
        data2.callback = (a)=>{
            TeamService.promoteNewLeader($scope.team,form.user,()=>{
                $scope.loadTeam($scope.team.id);
            });
        };
        Alerts.confirm(data2);
    };

    $scope.kickMember = function(form){
        let data2 = {title: "Kick member",text: `Are you sure you want to kick this member out of the team?`,
            confirmtext: "User kicked out!",confirmtitle:"Kicked out!!"};
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