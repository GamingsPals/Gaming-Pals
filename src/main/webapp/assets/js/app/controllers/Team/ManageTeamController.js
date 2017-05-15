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
            $location.path("team/"+form.name);
            SystemMessages.okmessage("Team edited!");
        })
    };
    $scope.leaveTeam = function(actor){
        data = {title: "Leave the Team",text: "Are you sure you want to leave this team?",confirmtitle:"You have left the Team",
        confirmtext: ""};
        data.callback =(a)=>{
            xhr.get(`api/team/${$scope.team.id}/leave`,(data2)=>{
                $location.path("team/"+$scope.team.name);
                SystemMessages.okmessage("You have left the Team!");
            })
        };
        Alerts.confirm(data);

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