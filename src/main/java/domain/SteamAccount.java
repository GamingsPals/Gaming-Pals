package domain;

import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.PROPERTY)
public class SteamAccount extends GameInfo {


    private String steamID;

    @Size(max = 500)
    @SafeHtml
    public String getSteamID() {
        return steamID;
    }

    public void setSteamID(String steamID) {
        this.steamID = steamID;
    }


}
