app.filter ('search', function(){
    return function(obj,search){
        let result = [];
        if(typeof obj==="undefined") return obj;
       obj.forEach((a)=>{
           if(typeof search.userAccount!=="undefined"){
               if(a.userAccount.username.toLowerCase().indexOf(search.userAccount.username.toLowerCase())===-1){
                   return false;
               }
           }
           if(typeof search.games!=="undefined"){
               let games = a.gameInfo.filter((g)=>{
                  return search.games.indexOf(g.game.id.toString())!==-1;
               });
               if(games.length!==search.games.length) return false;
           }
           if(typeof search.languages!=="undefined"){
               let languages = a.languages.filter((l)=>{
                   return search.languages.indexOf(l.language)!==-1;
               });
               if(languages.length!==search.languages.length) return false;
           }

           result.push(a);
       });

        return result;
    }
});