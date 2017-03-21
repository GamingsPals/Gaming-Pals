package services.apis.lol.Builder;


import com.google.gson.JsonElement;
import services.apis.lol.Client;
import services.apis.lol.Builder.Type.Type;
import services.apis.lol.Mapper;
import services.apis.lol.Utility.Regex;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityBuilder {

    protected Type type;
    protected String uri;
    protected Map<String, String> attributes = new HashMap<>();
    protected JsonElement data;

    public void load() throws IOException {
        Client c = new Client(uri);
        c.execute();
        Mapper mapper = new Mapper(c.getStringResponse());
        this.data = mapper.getJsonElement();
    }


    protected String buildUri() {
        this.uri = this.type.getUri();
        int counter = 0;
        while((this.uri.contains("{") && this.uri.contains("}")) || counter<20){
            Regex r = new Regex("\\{([a-zA-Z]*)\\}");
            List<String> results = r.findAll(this.uri);
            for(String e: results) {
                String attr = e.replace("{", "");
                attr  = attr.replace("}", "");
                if (attributes.get(attr) != null) {
                    this.uri = this.uri.replace(e,attributes.get(attr));
                }
            }
            ++counter;
        }
//    }
        return this.uri;
    }

    public Type getType(){
        return this.type;
    }
}
