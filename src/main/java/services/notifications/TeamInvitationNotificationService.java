package services.notifications;

import domain.Actor;
import domain.Team;
import domain.User;
import domain.notifications.TeamInvitationNotification;
import forms.TeamForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ActorRepository;
import repositories.notifications.TeamInvitationNotificationRepository;
import services.ActorService;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class TeamInvitationNotificationService {
    //Repositories
    @Autowired
    private TeamInvitationNotificationRepository teamInvitationNotificationRepository;

    @Autowired
    private ActorService actorService;


    public TeamInvitationNotification save(TeamInvitationNotification team) {

        Assert.notNull(team);
        return teamInvitationNotificationRepository.save(team);

    }

    public void delete(TeamInvitationNotification team) {

        Assert.notNull(team);
        teamInvitationNotificationRepository.delete(team);

    }

    public TeamInvitationNotification findOne(int teamId) {
        System.out.print(teamId);
        TeamInvitationNotification result= teamInvitationNotificationRepository.findOne(teamId);
        System.out.println(result);
        Assert.notNull(result);

        return result;

    }

    public Collection<TeamInvitationNotification> findAll() {

        Collection<TeamInvitationNotification> result;
        result = teamInvitationNotificationRepository.findAll();

        Assert.notNull(result);

        return result;

    }

    public boolean isNotification(Team team, User e) {
        return teamInvitationNotificationRepository.isNotification(team,e)  != null;
    }

    public Collection<TeamInvitationNotification> findAllNotRead() {
        User principal = (User) actorService.findActorByPrincipal();
        Assert.notNull(principal);
        return teamInvitationNotificationRepository.findAllNotRead(principal);
    }

    public Object findByActorNews(Actor actor) {
        return teamInvitationNotificationRepository.findByActorNews(actor);
    }
}
