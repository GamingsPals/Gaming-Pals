package forms;


import domain.Game;

import java.util.List;


public class SteamForm {

    public String id;
    public List<Game> games;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
