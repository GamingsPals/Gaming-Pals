package services.apis.lol;



import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;


public class Mapper {


    private JsonElement jsonElement;

    public Mapper(String json){
        try {
            this.jsonElement = new JsonParser().parse(json);
        }catch (Exception ignored){
            this.jsonElement = null;
        }
    }

    public JsonElement getJsonElement() throws IOException {
        return this.jsonElement;
    }

}
