package services;

import domain.Team;
import domain.Tournament;
import domain.User;
import forms.TeamForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
    private ConfigurationService configurationService;

    @Autowired
    private TeamInvitationNotificationService teamInvitationNotificationService;

    @Autowired
    private UserService userService;


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
        team.setUsers(null);
        team.setTournaments(null);
        team = save(team);
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


    public Team createTeamForm(TeamForm team) {
        User principal = (User) actorService.findByPrincipal();
        Team saved = new Team();
        saved.setName(team.getName());
        saved.setPicture(team.getPicture());
        saved.setLeader(principal);
        if(team.getPicture()==null){
            saved.setPicture(configurationService.getConfiguration().getDefaultTeamHeader());
        }else{
            if(team.getPicture().equals("") || team.getPicture().equals(" ")){
                saved.setPicture(configurationService.getConfiguration().getDefaultTeamHeader());
            }
        }
        System.out.println(team.getPassword());
        saved.setPassword(team.getPassword());
        if(team.getPassword()!=null){
            if (team.getPassword().equals("") || team.getPassword().equals(" ")) {
                saved.setPassword(null);
            }
        }
        List<User> members = new ArrayList<>();
        members.add(principal);
        saved.setUsers(members);
        saved = save(saved);
        for(User e:team.getMembers()){
            if(!teamInvitationNotificationService.isNotification(saved,e) && e.getId()!=principal.getId()) {
                teamInvitationNotificationService.newInvitation(e,saved);
            }
        }
        return saved;
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

    public Team findByName(String name) {
        Assert.notNull(name);

        return teamRepository.findByName(name);
    }

    public void edit(TeamForm teamForm, Team team) {
        User principal = userService.findByPrincipal();
        Assert.notNull(principal);
        Assert.notNull(teamForm);
        Assert.notNull(team);
        Assert.isTrue(team.getLeader().equals(principal));
        if(teamForm.getPassword()!=null){
            team.setPassword(teamForm.getPassword());
        }
        if(teamForm.getPicture()!=null){
            team.setPicture(teamForm.getPicture());
        }
        if(teamForm.getName()!=null){
            team.setName(teamForm.getName());
        }

        save(team);
    }

    public void leaveTeam(Team team){
        User user = userService.findByPrincipal();
        Assert.notNull(team);
        Assert.notNull(user);
        Assert.isTrue(!(team.getLeader().equals(user)));
        Assert.isTrue(team.getUsers().contains(user));

        Collection<User> users = new ArrayList<>(team.getUsers());
        users.remove(user);
        team.setUsers(users);

        save(team);
    }

    public void promoteLeader(Team team, User user) {
        Assert.notNull(team);
        Assert.notNull(user);
        User principal = userService.findByPrincipal();
        Assert.isTrue(team.getLeader().equals(principal));
        Assert.isTrue(team.getUsers().contains(user));
        Assert.isTrue(!(user.equals(principal)));

        team.setLeader(user);

        save(team);
    }

    public void enterByPassword(Team team, String password) {
        User principal = userService.findByPrincipal();
        Assert.notNull(principal);
        Assert.isTrue(!(team.getUsers().contains(principal)));
        Assert.notNull(password);

        if(team.getPassword().equals(password)){
            List<User> users = new ArrayList<>(team.getUsers());
            users.add(principal);
            team.setUsers(users);

            save(team);
        }
    }

    public void kickMemberTeam(Team team, User user) {
        User principal = userService.findByPrincipal();
        Assert.notNull(team);
        Assert.notNull(principal);
        Assert.isTrue(!(user.equals(principal)));
        Assert.isTrue((team.getLeader().equals(principal)));
        Assert.isTrue(team.getUsers().contains(user));
        List<User> users = new ArrayList<>(team.getUsers());
        users.remove(user);
        team.setUsers(users);

        save(team);
    }

    public void invite(Collection<User> users, Team team) {
        Assert.notNull(users);
        Assert.notNull(team);
        for(User u: users){
            Assert.isTrue(!team.getUsers().contains(u));
            teamInvitationNotificationService.newInvitation(u,team);
        }


    }
}
