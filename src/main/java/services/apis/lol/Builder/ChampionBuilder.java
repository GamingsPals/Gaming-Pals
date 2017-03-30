package services.apis.lol.Builder;


import services.apis.lol.LoLApi;
import services.apis.lol.Builder.Type.StaticDataType;
import services.apis.lol.Entity.Champion;

public class ChampionBuilder extends EntityBuilder {
	public ChampionBuilder(String id, String region) {
		this.attributes.put("version", "1.2");
		attributes.put("resource", "champion/{id}");
		attributes.put("parameters", "");
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
		champion.setKey(this.data.getAsJsonObject().get("key").getAsString());

		return champion;
	}
}
