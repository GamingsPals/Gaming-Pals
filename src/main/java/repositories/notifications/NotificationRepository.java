
package repositories.notifications;


import domain.Actor;
import domain.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("select n from Notification n where n.actor=?1 and n.readed=false")
    Collection<Notification> findByActorNews(Actor actor);
}
