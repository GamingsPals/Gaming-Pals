package services.notifications;

import domain.Actor;
import domain.Team;
import domain.User;
import domain.notifications.TeamInvitationNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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

    public void newInvitation(User e, Team team) {
        Assert.notNull(e);
        Assert.notNull(team);
        TeamInvitationNotification exists = teamInvitationNotificationRepository.
                findNotificationByUserTeam(e,team);
        if(exists==null) {
            TeamInvitationNotification teamInvitationNotification = new TeamInvitationNotification();
            teamInvitationNotification.setTeam(team);
            teamInvitationNotification.setUser(e);
            teamInvitationNotification.setActor(e);
            save(teamInvitationNotification);
        }
    }

    public void newInvitations(Collection<User> users, Team team) {
        Assert.notNull(users);
        Assert.notNull(team);
        for(User u: users){
            Assert.notNull(u);
            Assert.isTrue(!team.getUsers().contains(u));
            newInvitation(u,team);
        }
    }
}
