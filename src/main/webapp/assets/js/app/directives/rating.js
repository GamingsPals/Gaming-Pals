app.directive("rating",function(){
    return{
        restrict: "E",
        scope:{
            actor: "="
        },
        link: function(scope,element,attrs){
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