
package repositories.notifications;


import domain.Actor;
import domain.Team;
import domain.User;
import domain.notifications.TeamInvitationNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamInvitationNotificationRepository extends JpaRepository<TeamInvitationNotification, Integer> {

    @Query("select t from TeamInvitationNotification  t where t.team=?1 and t.user=?2")
    TeamInvitationNotification isNotification(Team team, User e);

    @Query("select t from TeamInvitationNotification t where t.readed=false and t.user=?1")
    Collection<TeamInvitationNotification> findAllNotRead(User principal);

    @Query("select n from TeamInvitationNotification n where n.actor=?1 and n.readed=false")
    Collection<TeamInvitationNotification> findByActorNews(Actor actor);

    @Query("select n from TeamInvitationNotification n where n.actor=?1 and n.team=?2 and n.readed=false")
    TeamInvitationNotification findNotificationByUserTeam(User e, Team team);
}
