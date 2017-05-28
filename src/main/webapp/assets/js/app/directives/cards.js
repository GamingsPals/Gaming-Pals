app.directive("userCard",function($compile,auth,AdminService,chat){
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
                scope.chat = chat;
                scope.AdminService = AdminService;
                let template = `
                <div class="card-header">
                <div class="card-header-right">
                 <rating actor="i"></rating>
                    </div>
                    <img class="card-header-header" ng-src="{{i.header}}">
                    <img class="card-header-avatar" ng-src="{{i.picture}}">
                 </div>
                 <div class="card-body">
                 <span class="float-right" admin-tools="i"></span>
                  <a href="profile/{{i.userAccount.username}}"> <h1>{{i.userAccount.username}} 
                  <i class="green3 fa fa-circle" aria-hidden="true" ng-show="chat.isConnected(i.id)"></i></h1></a>
                   <div class="col s8 x3" >
                   <h2>Games</h2>
                   <span tooltip="" ng-repeat="g in i.gameInfo">
                   <img class="game-icon open-tooltip" ng-src="{{g.game.picture}}"/>
                   <div class="card tooltip" game-card="g" user="i">
                    </div>
                        </span>
                   
                            </div>
                  <div class="flags col s3 x1">
                  <h2>Languages</h2>
                    <img  ng-repeat="lan in i.languages"
                          ng-src="assets/images/flags/{{lan.language}}.png" />
                </div>
                  </div>
                  <div class="card-footer">
                  <div class="col s7 x2">
                  <a class="button" 
                  ng-if="auth.principal.actor.id!=i.id " href="messages/{{i.userAccount.username}}"><i class="fa fa-envelope"></i> Message</a>
                    </div>
                  <div class="col s3 x1 float-right">
               
                           <div follow="i"></div>
                    </div>
                    <div class="clear-both"></div>
        
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
                     <span tooltip="" ng-repeat="a in i.users" >
                    <a class="open-tooltip" href="profile/{{a.userAccount.username}}">
                    <img class="profile-image" ng-class="(a.id==i.idLeader) ? 'team-leader' : ''" ng-src="{{a.picture}}">
                    </a>
                     <div class="card tooltip" user-card="a">
                    </div>
                </span>

                            </div>
              
                  </div>
                  <div class="card-footer">
                  <div class="col s7 x2">
                    </div>
                  <div class="col s3 x1 float-right">
                           <a class="button" href="team/{{i.name}}">Visit</a>
                    </div>
                    <div class="clear-both"></div>
        
                        </div>
                
            `;
                    $(element).html(template);
                    $compile(element.contents())(scope);
                }
            })
        }
    }
});
app.directive("gameCard",function($compile,localization){
    return{
        restrict: "A",
        scope: {
            "gameCard": "=",
            "user": "="
        },
        link: function(scope,element,attrs){
            $(element).addClass("blockcard");
            let updated = true;
            scope.$watch('gameCard',()=>{
                if(typeof scope.gameCard!=="undefined"){
                    scope.i = scope.gameCard;
                    let template = `
                <div class="card-header">
                     <h1>{{i.game.name}}</h1>
                     <img class="card-header-header" ng-src="{{i.game.header}}">
                     </div>
                     <div class="card-body">
                            <span class="float-right" games-tools="i" user="user"></span>
                     <h1>{{i.username}}</h1>
                     </div>
                     <div class="card-footer">
                     <div class="s12 x4">
                     <img ng-src="{{i.game.picture}}" class="game-icon" />
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

app.directive("tournamentCard",function($compile,localization,auth){
    return{
        restrict: "A",
        scope: {
            "tournamentCard": "="
        },
        link: function(scope,element,attrs){
            $(element).addClass("blockcard");
            let updated = true;
            scope.$watch('tournamentCard',()=>{
                scope.loc = localization;
                scope.auth = auth;
                if(typeof scope.tournamentCard!=="undefined"){
                    scope.i = scope.tournamentCard;
                    let template = `
    <div class="card-header">
        <div class="card-header-right">
            <a class="" href="tournament/{{i.id}}/awards"><i class="yellow3 fa fa-trophy"></i> </a>
        </div>
        <a href="tournament/{{i.id}}"><img class="card-header-header" ng-src="{{i.picture}}" /></a>
    </div>
    <div class="card-body"> 
        <h1><a href="tournament/{{i.id}}"> {{i.title}}</a></h1>
             <span class="float-right"
             ><img class="game-icon float-right" ng-src="{{i.game.picture}}"/>
             <div style="font-size:0.8em;">{{i.game.name}}</div></span>
        <p>{{i.description}}</p>
        <div><b>Joined {{loc.tournament.teams}}:</b> {{i.teams.length}}/{{i.numberTeams}} </div>
        <div><b>Players:</b> {{i.players}} (+2)</div>
        <div>
        <h2>Teams</h2>
        <span tooltip ng-repeat="u in i.teams">
        <img ng-src="{{u.picture}}" class="open-tooltip profile-image"/>
            <div class="tooltip card" team-card="u"></div>
            </span>
           </div>
           
    </div>
    <div class="card-footer">
        <div class="col s6 x2">
            <span ng-if="startedTournament(i)">
                {{loc.tournament.started}}
            </span>
            <span ng-if="!startedTournament(i)">
                 {{i.limitInscription | date:"dd MMM h:mm'h'"}}
            </span>
        </div>
        <div class="col s6 x2">
            <span ng-if="closedTournament(i)">
                {{loc.tournament.inscriptionClosed}} <i class="fa fa-lock small"></i>
            </span>
            <span ng-if="!closedTournament(i) && auth.hasRole('USER')">
                <a href="tournament/{{i.id}}" class="button"> {{loc.tournament.inscribe}}</a>
            </span>
        </div>
    </div>
                
            `;
                    scope.startedTournament = function (tournament) {
                        let now = new Date();
                        let limit = new Date(tournament.limitInscription);

                        return limit < now;
                    };
                    scope.closedTournament = function (tournament) {
                        return tournament.teams.length===+tournament.numberTeams || scope.startedTournament(tournament);
                    };

                    $(element).html(template);
                    $compile(element.contents())(scope);
                }
            })
        }
    }
});
