app.controller('AdminPanelController',function($scope,ActorService,middleware,
                                               AdminService,$routeParams,$location, SteamService,SweetAlert,localization){
    middleware.needRol("ADMIN,MODERATOR");
    $scope.getBannedUsers = ()=>{
        AdminService.getBannedUsers((a)=>{
            $scope.bannedusers = a;
        });
    };
    $scope.SteamService = SteamService;
    AdminService.addHookBannedUser((actor)=>{
        $scope.getBannedUsers();
    });

    $scope.getGames = function(value){
        if(typeof value==="undefined") return false;
      if(value.length>3){
          SteamService.all(value);
      }
    };

    $scope.tojson = function(value){
        $scope.selectedgame = JSON.parse(value);
        $scope.form = $scope.selectedgame;
        $scope.form.gameid = $scope.form.appid;
    };

    $scope.addGame =function(game){
        SteamService.addGame(game,((a)=>{
            SweetAlert.swal(localization.adminConfirm.gameA,`You have added ${game.name}succesfully`,localization.confirmProfile.successE);
            $scope.search = '';
            delete $scope.selectedgame;
            delete $scope.form;
        }));
    };

    $scope.getReportedUsers =  function(){
        AdminService.getReportedUsers((a)=>{
            $scope.reportedusers = a;
        });
    };

    $scope.deleteReport = function(report){
        AdminService.deleteReport(report,(a)=>{
            $scope.getReportedUsers();
        });
    };

    $scope.getBannedUsers();
    $scope.getReportedUsers();
    $scope.mode = $routeParams.menu;
    if(typeof $scope.mode ==="undefined"){
        $location.path("adminpanel/usersbanned");
    }


});