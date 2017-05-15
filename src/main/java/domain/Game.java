package domain;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Game extends DomainEntity {



    private String name;
    private Integer gameid;
    private String tag; // Este tag es un acronimo del nombre para ayudar con los assets (example: League of legends => lol => assets/images/lolicono.png)
    private Collection<GameInfo> gameInfos;
    private String header;
    private String picture;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    public Collection<GameInfo> getGameInfos() {
        return gameInfos;
    }

    public void setGameInfos(Collection<GameInfo> gameInfos) {
        this.gameInfos = gameInfos;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }
}
