package domain.notifications;


import domain.Actor;
import domain.DomainEntity;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Notification extends DomainEntity {

    public enum Type{
        Message, Follower, TeamInvitation
    }

    protected Actor actor;
    protected boolean readed;
    protected Type type;

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @ManyToOne
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
