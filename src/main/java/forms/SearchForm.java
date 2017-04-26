package forms;


import domain.Game;
import domain.Language;

import java.util.Collection;
import java.util.List;

public class SearchForm {

    private List<Game> games;
    private String username;
    private List<Language> languages;
    private Integer ratingAvg;
    private Integer page;
    private Integer limit;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }



    public Integer getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(Integer ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
