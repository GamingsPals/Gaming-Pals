app.controller('MainController',function($scope, localization, $rootScope, auth, SystemMessages, $sanitize,LoLStaticData
,ActorService,UserService,$location,NotificationService,socket,chat){
    localization.init($scope);
    $rootScope.loc = localization;
    $scope.auth = auth;
    $scope.ActorService = ActorService;
    $scope.notifications = NotificationService;
    $scope.auth.load();

    $scope.auth.addListener((data)=>{
            UserService.findAll(function(data){
                UserService.all = data;
            });
            if($scope.auth.isAuthenticated()){
                $scope.notifications.getNews();
                if (!socket.connected){
                    socket.init($rootScope);
                    chat.handleListeners();
                    socket.listen();
                }
            }else{
                socket.disconnect();
            }
    });

    $rootScope.csrf = csrf;
    $scope.MessageSystem = SystemMessages;
    $scope.sanitize = $sanitize;
    $scope.lolsd = LoLStaticData;
    $scope.lolsd.loadVersion(()=>{
        $scope.lolsd.loadChampions();
        $scope.lolsd.loadItems();
    });
    $scope.Math = Math;

    $scope.searchUsername = function(name){
        $location.path("search").search("username",name);
    };


});