app.controller("TeamController",function($scope,auth,middleware,$routeParams,xhr,TeamService,UserService,SweetAlert,$location){
    middleware.needRol("USER");
    $scope.notFound = true;
    $scope.url = "team/"+$routeParams.name;
    $scope.mode = $routeParams.menu;
    if(typeof $scope.mode ==="undefined"){
        $scope.mode = "members";
    }

    $scope.tabs=$scope.mode;

    $scope.loadTeam = (name)=>{
        TeamService.get(name,(a)=>{
            $scope.team = a.data.team;
            $scope.tournaments = a.data.tournaments;
            $scope.notFound = true;
        });
    };

    $scope.loadTeam($routeParams.name);

    $scope.isInTeam = (a)=>{
        if(typeof $scope.team==="undefined") return false;
        let result = $scope.team.users.find((b)=>{
            return a.id === b.id;
        });
        return (typeof result !== "undefined" && result !== false);
    };

    $scope.join = function(form){
        let password = form.password;
        xhr.get(`api/team/${$scope.team.id}/join?password=${password}`,(a)=>{
            SweetAlert.swal("You've joined the team!");
            $scope.loadTeam($scope.team.name);
            $location.path("team/"+$scope.team.name);
        },(a)=>{
            SweetAlert.warning("The password didn't match!")
        })
    };

    $scope.isLeader = (a)=>{
        if(typeof $scope.team==="undefined") return false;
        return $scope.team.idLeader===a.id;
    };
});