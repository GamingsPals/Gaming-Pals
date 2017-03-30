package services.apis.lol.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Summoner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonElement;

import services.apis.lol.Entity.MatchSummoner;
import services.apis.lol.LoLApi;
import services.apis.lol.Builder.Type.BasicType;
import services.apis.lol.Entity.Item;
import services.apis.lol.Entity.Match;


public class MatchBuilder extends EntityBuilder {
	
	private String region;

	public MatchBuilder(String idSummoner, String region) {
		this.region=region;
		this.attributes.put("version", "1.3");
		attributes.put("resource", "game/by-summoner/{id}/recent");
		attributes.put("parameters", "");
		attributes.put("id", idSummoner);
		attributes.put("region", region);
		this.type = new BasicType();
		attributes.put("apikey", LoLApi.APIKEY);
		this.buildUri();
	}
	public List<Match> getMatchs() throws IOException{
		List<Match> matchs= new ArrayList<Match>();
		for(JsonElement e : this.data.getAsJsonObject().get("games").getAsJsonArray()){
			Match match= new Match();
			match.setChampionId(e.getAsJsonObject().get("championId").getAsInt());
			match.setId(e.getAsJsonObject().get("gameId").getAsLong());
			match.setIsWin(e.getAsJsonObject().get("stats").getAsJsonObject().get("win").getAsBoolean());
			match.setCreatedGame(new Date(e.getAsJsonObject().get("createDate").getAsLong()));
			match.setKills(e.getAsJsonObject().get("stats").getAsJsonObject().get("championsKilled").getAsInt());
			match.setDeaths(e.getAsJsonObject().get("stats").getAsJsonObject().get("numDeaths").getAsInt());
			if (e.getAsJsonObject().get("stats").getAsJsonObject().get("assists")!=null) {
                match.setAssists(e.getAsJsonObject().get("stats").getAsJsonObject().get("assists").getAsInt());
            }
			List<MatchSummoner> summonerTeam=new ArrayList<>();
            List<MatchSummoner> enemyTeam=new ArrayList<>();
			for(JsonElement j : e.getAsJsonObject().get("fellowPlayers").getAsJsonArray()){
			    MatchSummoner matchSummoner = new MatchSummoner();
                matchSummoner.setSummonerId(j.getAsJsonObject().get("summonerId").getAsString());
                matchSummoner.setChampionId(j.getAsJsonObject().get("championId").getAsString());
				if(j.getAsJsonObject().get("teamId").getAsInt()==e.getAsJsonObject().get("teamId").getAsInt()){
					summonerTeam.add(matchSummoner);
				}else{
				   enemyTeam.add(matchSummoner);
                }
			}
			List<Integer> items = new ArrayList<>();
			for(int i=0;i<=9;i++){
				if(e.getAsJsonObject().get("stats").getAsJsonObject().get("item"+i)!=null){
					items.add(e.getAsJsonObject().get("stats").getAsJsonObject()
                            .get("item"+i).getAsInt());
				}
			}
			match.setGameId( e.getAsJsonObject().get("gameId").getAsString());
			match.setSummonerTeam(summonerTeam);
            match.setEnemyTeam(enemyTeam);
			match.setItemsGame(items);
			matchs.add(match);
		}
		return matchs;
	}

}
