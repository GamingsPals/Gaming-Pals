package domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

    String defaultAvatar;

    String defaultHeader;

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
}

