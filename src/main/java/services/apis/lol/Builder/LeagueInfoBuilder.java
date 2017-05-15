package services.apis.lol.Builder;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.League;
import domain.Summoner;
import services.apis.lol.Builder.Type.BasicType;
import services.apis.lol.LoLApi;

import java.util.ArrayList;
import java.util.List;

public class LeagueInfoBuilder extends EntityBuilder {

    private String id;

    public LeagueInfoBuilder(String id, String region){
        this.id = id;
        this.attributes.put("version","2.5");
        attributes.put("resource","league/by-summoner/{id}/entry");
        attributes.put("parameters","");
        attributes.put("id",id);
        attributes.put("region",region);
        this.type = new BasicType();
        attributes.put("apikey", LoLApi.APIKEY);
        this.buildUri();
    }

    public List<League> getSummonerLeagueInfo(){
        if(this.data != null && this.data.getAsJsonObject().get("status")==null){
        JsonArray array = this.data.getAsJsonObject().get(this.id)
                .getAsJsonArray();
        List<League> leagues = new ArrayList<>();
        for(JsonElement e: array){
            League l = new League();
            JsonObject entrie = e.getAsJsonObject().get("entries")
                    .getAsJsonArray().get(0).getAsJsonObject();
            l.setDivision(entrie.get("division").getAsString());
            l.setLosses(Integer.valueOf(entrie.get("losses").getAsString()));
            l.setWins(Integer.valueOf(entrie.get("wins").getAsString()));
            l.setPoints(Integer.valueOf(entrie.get("leaguePoints").getAsString()));
            l.setRankedType(e.getAsJsonObject().get("queue").getAsString());
            l.setTier(e.getAsJsonObject().get("tier").getAsString());
            leagues.add(l);
        }
       return leagues;
    }
        return null;
    }

}
