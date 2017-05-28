package forms;

import domain.User;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamInvitationForm {

    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
