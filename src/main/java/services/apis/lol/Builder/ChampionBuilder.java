package services.apis.lol.Builder;


import services.apis.lol.LoLApi;
import services.apis.lol.Builder.Type.StaticDataType;
import services.apis.lol.Entity.Champion;

public class ChampionBuilder extends EntityBuilder {
	public ChampionBuilder(String id, String region) {
		this.attributes.put("version", "1.2");
		attributes.put("resource", "champion/{id}");
		attributes.put("parameters", "&locale=es_ES&champData=all");
		attributes.put("id", id);
		attributes.put("region", region);
		this.type = new StaticDataType();
		attributes.put("apikey", LoLApi.APIKEY);
		this.buildUri();
	}

	public Champion getChampion() {
		Champion champion = new Champion();
		champion.setId(this.data.getAsJsonObject().get("id").getAsInt());
		champion.setName(this.data.getAsJsonObject().get("name").getAsString());
		champion.setUrlImage("http://ddragon.leagueoflegends.com/cdn/7.5.2/img/champion/"+this.data.getAsJsonObject().get("image").getAsJsonObject().get("full").getAsString());

		return champion;
	}
}
