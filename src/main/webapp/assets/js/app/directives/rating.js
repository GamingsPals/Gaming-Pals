app.directive("rating",function(){
    return{
        restrict: "E",
        scope:{
            actor: "="
        },
        link: function(scope,element,attrs){
            let calculateRating = function(actor){
                actor.avgknowledge = 0;
                actor.avgattitude = 0;
                actor.avgskill = 0;
                let object = this;
                actor.ratingsReceived.forEach(function(a){
                    actor.avgknowledge += a.knowledge;
                    actor.avgattitude += a.attitude;
                    actor.avgskill += a.skill;
                });
                let nRatings = (actor.ratingsReceived.length>0) ? actor.ratingsReceived.length : 1;
                actor.avgknowledge = Math.round((actor.avgknowledge/nRatings) * 100) / 100;
                actor.avgattitude = Math.round((actor.avgattitude/nRatings) * 100) / 100;
                actor.avgskill = Math.round((actor.avgskill/nRatings) * 100) / 100;
                actor.nRatings = actor.ratingsReceived.length;
                actor.avgrating = (actor.avgattitude + actor.avgskill + actor.avgknowledge) / 3;
                return actor;
            };
            scope.actor = calculateRating(scope.actor);
            let avgattitude = scope.actor.avgattitude;
            let avgskill = scope.actor.avgskill;
            let avgknowledge = scope.actor.avgknowledge;
            if (typeof avgattitude==="undefined"){
                avgattitude = scope.actor.attitudeAvg;
                avgskill = scope.actor.skillAvg;
                avgknowledge = scope.actor.knowledgeAvg;
            }
            let html = `<ul class="list-horizontal">
                        <li class="col">
                            <div class="label bg-green3" title="Attitude">A ${avgattitude}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-red3" title="Skill">S ${avgskill}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-magenta3" title="Knowledge">K ${avgknowledge}</div>
                        </li>
                    </ul>`;
            $(element).html(html);

        }
    }
});