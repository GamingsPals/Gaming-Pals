app.directive("rating",function(){
    return{
        restrict: "E",
        scope:{
            actor: "="
        },
        link: function(scope,element,attrs){
            let html = `<ul class="list-horizontal">
                        <li class="col">
                            <div class="label bg-green3" title="Attitude">A ${scope.actor.avgattitude}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-red3" title="Skill">S ${scope.actor.avgskill}</div>
                        </li>
                        <li class="col">
                            <div class="label bg-magenta3" title="Knowledge">K ${scope.actor.avgknowledge}</div>
                        </li>
                    </ul>`;
            $(element).html(html);

        }
    }
});