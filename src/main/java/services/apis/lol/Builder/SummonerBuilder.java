package services.apis.lol.Builder;


import domain.Summoner;
import services.apis.lol.Builder.Type.BasicType;
import services.apis.lol.LoLApi;
import services.apis.lol.Utility.StringsUtility;

import java.io.IOException;
import java.util.List;


public class SummonerBuilder extends EntityBuilder {

    private String name;
    private String region;


    public SummonerBuilder(String names, String region){
        this.attributes.put("version","1.4");
        this.region = region;
        attributes.put("resource","summoner/by-name/{name}");
        attributes.put("parameters","");
        attributes.put("name",names);
        attributes.put("region",region);
        this.type = new BasicType();
        attributes.put("apikey", LoLApi.APIKEY);
        this.buildUri();
    }
    public SummonerBuilder(List<Integer> ids, String region) throws IOException {
        this.attributes.put("version","1.4");
        this.region = region;
        attributes.put("resource","summoner/{id}");
        attributes.put("parameters","");
        attributes.put("id", StringsUtility.ListToStringComma(ids));
        attributes.put("region",region);
        this.type = new BasicType();
        attributes.put("apikey", LoLApi.APIKEY);
        this.buildUri();
    }

    public SummonerBuilder(Integer id, String region){
        this.attributes.put("version","1.4");
        this.region = region;
        attributes.put("resource","summoner/{id}");
        attributes.put("parameters","");
        attributes.put("id",id.toString());
        attributes.put("region",region);
        this.type = new BasicType();
        attributes.put("apikey", LoLApi.APIKEY);
        this.buildUri();
    }


    public Summoner getSummoner(String name){
        if (data == null) return null;
        try {
            Summoner summoner = new Summoner();
            summoner.setIdSummoner(this.data.getAsJsonObject().get(name).getAsJsonObject().get("id").getAsInt());
            summoner.setProfileIconId(this.data.getAsJsonObject().get(name).getAsJsonObject().get("profileIconId")
                    .getAsInt());
            summoner.setUsername(this.data.getAsJsonObject().get(name).getAsJsonObject().get("name")
                    .getAsString());
            summoner.setRevisionDate(this.data.getAsJsonObject().get(name).getAsJsonObject().get("revisionDate")
                    .getAsString());
            summoner.setSummonerLevel(this.data.getAsJsonObject().get(name).getAsJsonObject().get("summonerLevel")
                    .getAsInt());
            summoner.setRegion(region);

            return summoner;
        }catch (Exception e){
            return null;
        }

    }

}
