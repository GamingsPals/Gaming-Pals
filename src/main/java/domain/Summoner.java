package domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class Summoner extends GameInfo {

    private Integer idSummoner;
    private Integer profileIconId;
    private String revisionDate;
    private Integer summonerLevel;
    private List<League> leagues;
    private String region;



    public Integer getIdSummoner() {
        return idSummoner;
    }

    public void setIdSummoner(Integer id) {
        this.idSummoner = id;
    }

    public Integer getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Integer profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(String revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Integer getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Integer summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    @OneToMany(mappedBy = "summoner",cascade = CascadeType.ALL)
    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
