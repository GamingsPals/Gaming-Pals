package domain;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Access(AccessType.PROPERTY)
public class Game extends DomainEntity implements Serializable {



    private String name;
    private String gameid;
    private String tag; // Este tag es un acronimo del nombre para ayudar con los assets (example: League of legends => lol => assets/images/lolicono.png)
    private Collection<GameInfo> gameInfos;
    private String header;
    private String picture;
    private Collection<Tournament> tournaments;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    public Collection<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Collection<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @SafeHtml
    @NotBlank
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    @NotBlank
    @SafeHtml
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    @NotBlank
    @SafeHtml
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotBlank
    @SafeHtml
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

    @NotBlank
    @SafeHtml
    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }
}
