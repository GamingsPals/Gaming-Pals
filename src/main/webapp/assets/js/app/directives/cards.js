app.directive("userCard",function($compile,auth){
    return{
        restrict: "A",
        scope: {
            "userCard": "="
        },
        link: function(scope,element,attrs){
            $(element).addClass("blockcard");
            let updated = true;
            scope.$watch('userCard',()=>{
                if(typeof scope.userCard!=="undefined"){
                scope.i = scope.userCard;
                scope.auth = auth;
                let template = `
                <div class="card-header">
                <div class="card-header-right">
                 <rating actor="i"></rating>
                    </div>
                    <img class="card-header-header" ng-src="{{i.header}}">
                    <img class="card-header-avatar" ng-src="{{i.picture}}">
                 </div>
                 <div class="card-body">
                  <a href="profile/{{i.userAccount.username}}"> <h1>{{i.userAccount.username}}</h1></a>
                   <div class="col s8 x3" >
                   <h2>Games</h2>
                   <img ng-repeat="g in i.gameInfo" style="width:80px" ng-src="assets/images/games/icons/{{g.game.tag}}icon.png"/>
                            </div>
                  <div class="flags col s3 x1">
                  <h2>Languages</h2>
                    <img  ng-repeat="lan in i.languages"
                          ng-src="assets/images/flags/{{lan.language}}.png" />
                </div>
                  </div>
                  <div class="card-footer">
                  <div class="col s8 x3">
                  <a class="button" ng-if="auth.principal.actor.id!=i.id" href="messages/{{i.userAccount.username}}"><i class="fa fa-envelope"></i> Message</a>
                    </div>
                  <div class="col s3 x1">
                           <div follow="i"></div>
                    </div>
        
                        </div>
                
            `;
                $(element).html(template);
                $compile(element.contents())(scope);
                }
            })
        }
    }
});

app.directive("teamCard",function($compile){
    return{
        restrict: "A",
        scope: {
            "teamCard": "="
        },
        link: function(scope,element,attrs){
            $(element).addClass("blockcard");
            let updated = true;
            scope.$watch('teamCard',()=>{
                if(typeof scope.teamCard!=="undefined"){
                    scope.i = scope.teamCard;
                    let template = `
                <div class="card-header">
                    <img class="card-header-header" ng-src="{{i.picture}}">
                 </div>
                 <div class="card-body">
                  <a href="team/{{i.id}}"> <h1>{{i.name}}</h1></a>
                   <div class="col s8 x3" >
                   <h2>Members</h2>
                    <a ng-repeat="a in i.users" href="profile/{{a.userAccount.username}}">
                    <img class="profile-image" ng-src="{{a.picture}}">
                </a>
                            </div>
              
                  </div>
                  <div class="card-footer">
                  <div class="col s8 x3">
                    </div>
                  <div class="col s3 x1">
                           <a class="button" href="team/{{i.name}}">Visit</a>
                    </div>
        
                        </div>
                
            `;
                    $(element).html(template);
                    $compile(element.contents())(scope);
                }
            })
        }
    }
});