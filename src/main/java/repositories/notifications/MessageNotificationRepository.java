
package repositories.notifications;


import domain.Actor;
import domain.notifications.MessageNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import services.notifications.MessageNotificationService;

import java.util.Collection;

@Repository
public interface MessageNotificationRepository extends JpaRepository<MessageNotification, Integer> {

    @Query("select m from MessageNotification m where m.sender=?1 and m.receiver=?2")
    public MessageNotification isNotification(Actor sender, Actor receiver);

    @Query("select n from MessageNotification n where n.actor=?1 and n.readed=false")
    Collection<MessageNotificationService> findAllByActorNews(Actor actor);
}
