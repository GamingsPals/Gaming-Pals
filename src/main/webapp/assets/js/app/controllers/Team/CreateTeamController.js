app.controller('CreateTeamController',function($scope,UserService,xhr,$location,dialog,middleware,SweetAlert,SystemMessages,
localization){
    middleware.needRol("USER");

    $scope.teamform = {};
    $scope.teamform.members = [];
    $scope.selectedmembers = [];
    UserService.addCallback((a)=>{
        $scope.allusers = a.slice();
    });
    UserService.findAll();
    $scope.addMember = function(a){
        $scope.selectedmembers.push(a);
        $scope.teamform.members.push(a.id);
        $scope.allusers.splice($scope.allusers.indexOf(a),1);
        $scope.showAutoComplete = false;
    };

    $scope.passwordInfo = function(){
        SweetAlert.swal($scope.loc.whatispassword);
    };

    let dialog2 = dialog.open("team/createTeam",$scope);
    dialog.redirect(dialog2,(a)=>{

    });


    $scope.removeMember = function(a){
        let key = $scope.selectedmembers.indexOf(a);
        let key2 = $scope.teamform.members.indexOf(a);
        $scope.selectedmembers.splice(key,1);
        $scope.teamform.members.splice(key2,1);
        $scope.allusers.push(a);
        $scope.showAutoComplete = false;
    };

    $scope.createTeam = function(form){
        let data = form;
        xhr.post("api/team/create",data,(data2)=>{
            $location.path(`team/${data2.data.name}`);
            dialog.close(dialog2,()=>{
                $location.path(`team/${data2.data.id}`);
                $scope.error = null;
                SystemMessages.okmessage(localization.team.created)
            },()=>{
                $scope.error = localization.error;
            });

        })
    }
});