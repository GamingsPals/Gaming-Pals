package forms;


public class SearchForm {

    private String game;
    private String tier;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "game='" + game + '\'' +
                ", tier='" + tier + '\'' +
                '}';
    }
}
