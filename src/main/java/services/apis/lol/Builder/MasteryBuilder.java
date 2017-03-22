package services.apis.lol.Builder;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import services.apis.lol.LoLApi;
import services.apis.lol.Builder.Type.BasicType;
import services.apis.lol.Entity.Mastery;

public class MasteryBuilder extends EntityBuilder  {
	private String id;
	public MasteryBuilder(String summonerId, String region){
        this.attributes.put("version","1.4");
        this.id=summonerId;
        attributes.put("resource","summoner/{id}/masteries");
        attributes.put("parameters","");
        attributes.put("id",summonerId);
        attributes.put("region",region);
        this.type = new BasicType();
        attributes.put("apikey", LoLApi.APIKEY);
        this.buildUri();
    }
	public List<Mastery> getMasteries(){
		List<Mastery> masteries=new ArrayList<Mastery>();
		for(JsonElement e: this.data.getAsJsonObject().get(this.id).getAsJsonObject().get("pages").getAsJsonArray()){
			Mastery m=new Mastery();
			m.setName(e.getAsJsonObject().get("name").getAsString());
			masteries.add(m);
		}
		return masteries;
	}
}
