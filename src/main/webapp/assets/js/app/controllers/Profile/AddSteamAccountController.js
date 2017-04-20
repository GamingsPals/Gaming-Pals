app.controller("AddSteamAccountController",function($scope,xhr,UserService,dialog){
    $scope.searched = false;
    $scope.validateSteam = function(){
        console.log(typeof $scope.steam.games);
        console.log($scope.steam.games[0]);
        xhr.post("api/steam/add",$scope.steam,(a)=>{
            dialog.closeAll();
            $scope.ActorService.UserProfile();
        },(p)=>{
            $scope.error = "There were some errors, please try again or check your Steam ID";
        })
    };

    $scope.checkGames = function(){
        xhr.get("api/steam/"+$scope.steam.id,((a)=>{
            $scope.games = a.data;
            $scope.searched = true;
        }),(p)=>{
            $scope.error = "There were some errors, please try again or check your Steam ID";
            console.log("lol");
        })
    }
});