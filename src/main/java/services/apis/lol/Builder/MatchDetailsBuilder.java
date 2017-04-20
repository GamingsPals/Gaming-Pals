package services.apis.lol.Builder;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import services.apis.lol.Builder.Type.BasicType;
import services.apis.lol.Builder.Type.StaticDataType;
import services.apis.lol.LoLApi;

import javax.rmi.CORBA.ValueHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MatchDetailsBuilder extends EntityBuilder {

    private String region;
    private String idMatch;

    public MatchDetailsBuilder(String id, String region) {
        this.attributes.put("version", "2.2");
        attributes.put("resource", "match/{id}");
        attributes.put("parameters", "&includeTimeline=false");
        attributes.put("id", id);
        attributes.put("region", region);
        this.type = new BasicType();
        attributes.put("apikey", LoLApi.APIKEY);
        this.idMatch = id;
        this.region = region;
        this.buildUri();
    }

    public Map<String, Object> getMatch() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object>  map = mapper.readValue(json, new TypeReference<Map<Object, Object>>(){});

        return map;
    }
}
