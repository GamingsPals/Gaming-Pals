
package repositories.notifications;


import domain.Actor;
import domain.notifications.FollowNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FollowNotificationRepository extends JpaRepository<FollowNotification, Integer> {

    @Query("select u from FollowNotification u where u.follower=?1 and u.following=?2")
    FollowNotification isNotification(Actor follower, Actor following);


    @Query("select n from FollowNotification n where n.actor=?1 and n.readed=false")
    Collection<FollowNotification> findActorByNews(Actor actor);
}
