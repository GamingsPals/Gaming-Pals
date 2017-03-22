package domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public class League extends DomainEntity {



    private String rankedType;
    private String tier;
    private Integer wins;
    private Integer losses;
    private String division;
    private Integer points;
    private Summoner summoner;

    public String getRankedType() {
        return rankedType;
    }

    public void setRankedType(String rankedType) {
        this.rankedType = rankedType;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

}
