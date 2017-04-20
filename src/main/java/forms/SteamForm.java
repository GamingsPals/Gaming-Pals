package forms;


import domain.Game;

import java.util.Collection;
import java.util.List;


public class SteamForm {

    public String id;
    public Collection<Game> games;
    public String username;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<Game> getGames() {
        return games;
    }

    public void setGames(Collection<Game> games) {
        this.games = games;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
