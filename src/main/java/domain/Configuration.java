package domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

    private String defaultAvatar;

    private String defaultHeader;

    private String defaultTeamHeader;

    private String defaultTournamentHeader;

    public String getDefaultTournamentHeader() {
        return defaultTournamentHeader;
    }

    public void setDefaultTournamentHeader(String defaultTournamentHeader) {
        this.defaultTournamentHeader = defaultTournamentHeader;
    }

    public String getDefaultAvatar() {
        return defaultAvatar;
    }

    public void setDefaultAvatar(String defaultAvatar) {
        this.defaultAvatar = defaultAvatar;
    }

    public String getDefaultHeader() {
        return defaultHeader;
    }

    public void setDefaultHeader(String defaultHeader) {
        this.defaultHeader = defaultHeader;
    }

    public String getDefaultTeamHeader() {
        return defaultTeamHeader;
    }

    public void setDefaultTeamHeader(String defaultTeamHeader) {
        this.defaultTeamHeader = defaultTeamHeader;
    }
}

