package services;

import domain.Team;
import domain.Tournament;
import domain.User;
import domain.notifications.TeamInvitationNotification;
import forms.TeamForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TeamRepository;
import services.notifications.TeamInvitationNotificationService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class TeamService {
    //Repositories
    @Autowired
    private TeamRepository teamRepository;

    //Services
    @Autowired
	private Validator validator;

    @Autowired
    private UserService actorService;

    @Autowired
    private TeamInvitationNotificationService teamInvitationNotificationService;


    //Constructor
    public TeamService(){super();}

    //CRUD METHODS
    public Team create(){
        Team result = new Team();
        result.setUsers(new ArrayList<User>());
        result.setTournaments(new ArrayList<Tournament>());

        return result;
    }

    public Team save(Team team) {

        Assert.notNull(team);
        return teamRepository.save(team);

    }

    public void delete(Team team) {

        Assert.notNull(team);
        teamRepository.delete(team);

    }

    public Team findOne(int teamId) {

        Team result=teamRepository.findOne(teamId);

        Assert.notNull(result);

        return result;

    }

    public Collection<Team> findAll() {

        Collection<Team> result;
        result = teamRepository.findAll();

        Assert.notNull(result);

        return result;

    }


    public void createTeamForm(TeamForm team) {
        User principal = (User) actorService.findByPrincipal();
        Team saved = new Team();
        saved.setName(team.getName());
        saved.setPicture(team.getPicture());
        List<User> members = new ArrayList<>();
        members.add(principal);
        saved.setUsers(members);
        saved = save(saved);
        for(User e:team.getMembers()){
            if(!teamInvitationNotificationService.isNotification(saved,e) && e.getId()!=principal.getId()) {
                TeamInvitationNotification teamInvitationNotification = new TeamInvitationNotification();
                teamInvitationNotification.setTeam(saved);
                teamInvitationNotification.setUser(e);
                teamInvitationNotification.setActor(e);
                teamInvitationNotificationService.save(teamInvitationNotification);
            }
        }
    }

    public Boolean isUserInTeam(Team teamId, User principal) {
        boolean result = false;
        for(User e : teamId.getUsers()){
            if(e.getId()==principal.getId()){
                result = true;
                break;
            }
        }

        return result;
    }
}
