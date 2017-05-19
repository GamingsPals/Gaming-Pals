package services.apis.steam;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.codehaus.jackson.map.ObjectMapper;
import services.apis.lol.Client;
import services.apis.lol.Mapper;
import services.apis.steam.Entity.Game;
import services.apis.steam.Entity.Games;
import services.apis.steam.Entity.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Builder {

    private String uri;
    private String json;
    protected JsonElement data;

    public Builder(String id){
        this.uri = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=BEE9282312CE5972EB98400ECD05FE3C&steamid="+id+"&include_appinfo=1";
    }

    public Builder(){
        this.uri ="http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
    }

    public void load() throws IOException {
        Client c = new Client(uri);
        c.execute();
        this.json = c.getStringResponse();
        Mapper mapper = new Mapper( this.json );
        this.data = mapper.getJsonElement();
    }

    public List<Game> getGames() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Response games = mapper.readValue(this.json, Response.class);

        return  games.getResponse().getGame();
    }

    public List<Game> getAllGames() throws IOException{
        Mapper mapper = new Mapper( this.json );
        this.data = mapper.getJsonElement();
        JsonElement applist = this.data.getAsJsonObject().get("applist");
        JsonElement apps = applist.getAsJsonObject().get("apps");
        JsonElement app = apps.getAsJsonObject().get("app").getAsJsonArray();
        ObjectMapper objectMapper = new ObjectMapper();
        Game[] games = objectMapper.readValue(app.toString(),Game[].class);

        return new ArrayList<Game>(Arrays.asList(games));

    }
}
