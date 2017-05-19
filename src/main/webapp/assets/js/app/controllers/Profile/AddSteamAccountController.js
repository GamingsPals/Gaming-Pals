app.controller("AddSteamAccountController",function($scope,xhr,UserService,dialog){
    $scope.searched = false;
    $scope.validateSteam = function(){
        xhr.post("api/steam/add",$scope.steam,(a)=>{
            dialog.closeAll();
            $scope.ActorService.UserProfile();
            $scope.error = "";
        },(p)=>{
            $scope.error = "There were some errors, please try again or check your Steam ID";
        })
    };

    $scope.checkGames = function(){
        xhr.get("api/steam/"+$scope.steam.id,((a)=>{
            $scope.games = a.data;
            $scope.searched = true;
            $scope.error = "";
        }),(p)=>{
            $scope.error = "There were some errors, please try again or check your Steam ID";
        })
    }
});