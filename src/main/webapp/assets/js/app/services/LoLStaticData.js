app.service("LoLStaticData",function(xhr){
    this.version = "7.6.1";

    this.loadVersion = function(callback){
        let object = this;
        xhr.get("https://ddragon.leagueoflegends.com/api/versions.json",function(data){
            object.version = data.data[0];
            if (typeof callback!=="undefined"){
                callback();
            }
        })
    };

    this.loadItems = function(){
        let object = this;
        xhr.get(`http://ddragon.leagueoflegends.com/cdn/${this.version}/data/en_US/item.json`, function(data){
            object.items = data.data.data;
        })
    };

    this.getItemIcon = function(item){
        return `//ddragon.leagueoflegends.com/cdn/${this.version}/img/item/${item}.png`;
    };

    this.getProfileIcon = function(icon){
        return `//ddragon.leagueoflegends.com/cdn/${this.version}/img/profileicon/${icon}.png`;
    };

    this.getChampionIcon = function(champion){
        return `//ddragon.leagueoflegends.com/cdn/${this.version}/img/champion/${champion}.png`;
    };

    this.loadChampions = function(){
        let object = this;
        xhr.get(`http://ddragon.leagueoflegends.com/cdn/${this.version}/data/en_US/champion.json`, function(data){
            object.champions = data.data.data;
        })
    };

    this.findChampionById = function(id){
        let champion = null;
        for (let property in this.champions) {
            if (this.champions.hasOwnProperty(property)) {
               if (this.champions[property].key == parseInt(id)){
                   champion = this.champions[property];
               }
            }
        }
        return champion;
    }
});