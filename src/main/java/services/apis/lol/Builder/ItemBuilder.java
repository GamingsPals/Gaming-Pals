package services.apis.lol.Builder;


import services.apis.lol.LoLApi;
import services.apis.lol.Builder.Type.StaticDataType;
import services.apis.lol.Entity.Item;

public class ItemBuilder extends EntityBuilder {

	public ItemBuilder(String id, String region) {
		this.attributes.put("version", "1.2");
		attributes.put("resource", "item/{id}");
		attributes.put("parameters", "&locale=es_ES&itemData=all");
		attributes.put("id", id);
		attributes.put("region", region);
		this.type = new StaticDataType();
		attributes.put("apikey", LoLApi.APIKEY);
		this.buildUri();
	}

	public Item getItem() {
		Item item = new Item();
		item.setId(this.data.getAsJsonObject().get("id").getAsInt());
		item.setName(this.data.getAsJsonObject().get("name").getAsString());
		item.setUrlImage("http://ddragon.leagueoflegends.com/cdn/7.5.2/img/item/"+this.data.getAsJsonObject().get("image").getAsJsonObject().get("full").getAsString());

		return item;
	}
}
