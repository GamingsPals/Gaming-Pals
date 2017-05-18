app.filter ('orderByLikes', function(){
    function getPuntuationLike(value){
        let likes = value.likes;
        let puntuacion = 0;
        likes.forEach((a)=>{
            if(a.liked){
                puntuacion = puntuacion+1;
            }else{
                puntuacion = puntuacion-1;
            }
        });
        return puntuacion;
    }
    return function(obj){
        obj.sort(function(a,b){
            let pa = getPuntuationLike(a);
            let pb = getPuntuationLike(b);
            if(pa===pb){
                console.log("ey");
                return (b.created<a.created) ? -1 : 1;
            }
            return (pb<pa) ? -1 : 1;
        });

        return obj;
    }
});