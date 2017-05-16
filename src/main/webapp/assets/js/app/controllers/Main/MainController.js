app.controller('MainController',function($scope, localization, $rootScope, auth, SystemMessages, $sanitize
,ActorService,UserService,$location,NotificationService,socket,chat, dialog,PaginationService,AdminService,TournamentService,
GameInfoService,SweetAlert){
    localization.init($scope);
    $scope.AdminService = AdminService;
    $scope.pagination = PaginationService;
    $scope.alert = SweetAlert;
    $scope.TournamentService = TournamentService;
    $rootScope.loc = localization;
    $scope.auth = auth;
    $scope.ActorService = ActorService;
    $scope.date = new Date();
    $scope.tomorrow = new Date();
    $scope.notifications = NotificationService;
    $scope.GameInfoService = GameInfoService;
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
    $scope.$location = $location;
    $rootScope.csrf = csrf;
    $scope.SystemMessages = SystemMessages;
    $scope.sanitize = $sanitize;
    $scope.Math = Math;

    $scope.searchUsername = function(name){
        $location.path("search").search("username",name);
    };

    $scope.legalIssues = function () {
        dialog.open("legalIssues",$scope);
    };

    $scope.aboutUs = function () {
        dialog.open("aboutUs",$scope);
    };
    $scope.checkProtocol = function(){
        if($location.protocol()==="http"  && window.location.hostname!=="localhost"){
            window.location = 'https://' + window.location.hostname +
                ":"+window.location.port+window.location.pathname + window.location.hash;
        }
    };
    $scope.checkProtocol();


});