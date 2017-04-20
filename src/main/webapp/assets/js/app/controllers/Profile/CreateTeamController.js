app.controller('CreateTeamController',function($scope,UserService,xhr,$location,dialog,middleware){
    middleware.needRol("ANY");
    $scope.teamform = {};
    $scope.teamform.members = [];
    $scope.selectedmembers = [];
    $scope.allusers = UserService.alluser.slice();
    $scope.addMember = function(a){
        $scope.selectedmembers.push(a);
        $scope.teamform.members.push(a.id);
        $scope.allusers.splice($scope.allusers.indexOf(a),1);
        $scope.showAutoComplete = false;
    };

    $scope.createTeam = function(){
        let data = $scope.teamform;
        xhr.post("api/team/create",data,(data)=>{
            dialog.closeAll();
            $location.path(`team/${data.data.name}`);
        })
    }
});