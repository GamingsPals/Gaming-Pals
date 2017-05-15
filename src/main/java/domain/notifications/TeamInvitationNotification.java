package domain.notifications;

import domain.Team;
import domain.User;
import domain.notifications.Notification;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public class TeamInvitationNotification extends Notification {

    private Team team;
    private User user;

    public TeamInvitationNotification() {
        this.type = Type.TeamInvitation;
    }

    @OneToOne
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @JsonIgnore
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
