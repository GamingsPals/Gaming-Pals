package services.apis.lol.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonElement;

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
			ChampionBuilder championBuilder=new ChampionBuilder(e.getAsJsonObject().get("championId").getAsString(),this.region);
			championBuilder.load();
			match.setChampionId(championBuilder.getChampion());
			match.setId(e.getAsJsonObject().get("gameId").getAsLong());
			match.setIsWin(e.getAsJsonObject().get("stats").getAsJsonObject().get("win").getAsBoolean());
			match.setCreatedGame(new Date(e.getAsJsonObject().get("createDate").getAsLong()));
			match.setKills(e.getAsJsonObject().get("stats").getAsJsonObject().get("championsKilled").getAsInt());
			match.setDeaths(e.getAsJsonObject().get("stats").getAsJsonObject().get("numDeaths").getAsInt());
			match.setAssists(e.getAsJsonObject().get("stats").getAsJsonObject().get("assists").getAsInt());
			List<Integer> summonerIdTeam=new ArrayList<Integer>();
			for(JsonElement j : e.getAsJsonObject().get("fellowPlayers").getAsJsonArray()){
				if(j.getAsJsonObject().get("teamId").getAsInt()==e.getAsJsonObject().get("teamId").getAsInt()){
					summonerIdTeam.add(j.getAsJsonObject().get("summonerId").getAsInt());
				}
			}
			List<Item> items = new ArrayList<Item>();
			for(int i=0;i<=9;i++){
				if(e.getAsJsonObject().get("stats").getAsJsonObject().get("item"+i)!=null){
					ItemBuilder itemBuilder=new ItemBuilder(e.getAsJsonObject().get("stats").getAsJsonObject().get("item"+i).toString(), region);
					itemBuilder.load();
					items.add(itemBuilder.getItem());
				}
			}
			match.setSummonerIdTeam(summonerIdTeam);
			match.setItemsGame(items);
			matchs.add(match);
		}
		return matchs;
	}
}
