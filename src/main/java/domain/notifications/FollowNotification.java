package domain.notifications;


import domain.User;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class FollowNotification extends Notification{

    private User follower;
    private User following;

    public FollowNotification() {
        this.type = Type.Follower;
    }

    @OneToOne
    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    @OneToOne
    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
