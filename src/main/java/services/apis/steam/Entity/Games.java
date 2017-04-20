package services.apis.steam.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Games {

    private List<Game> game;

    public List<Game> getGame() {
        return game;
    }

    public void setGames(List<Game> game) {
        this.game = game;
    }
}
