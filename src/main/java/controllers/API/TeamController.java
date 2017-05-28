package controllers.API;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Actor;
import domain.Team;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import forms.TeamForm;
import services.ActorService;
import services.notifications.TeamInvitationNotificationService;
import services.TeamService;
import services.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TeamController extends ApiAbstractController{
	
	@Autowired
	private TeamService teamService;

	@Autowired
    private ActorService actorService;

	@Autowired
	private UserService userService;

	@Autowired
    private TeamInvitationNotificationService teamInvitationNotificationService;

	@RequestMapping(value = "/team/create", method = RequestMethod.POST)
	public Object create(TeamForm team, HttpServletRequest request, HttpServletResponse response, BindingResult binding) {
        User principal;
		try {
			Assert.notNull(team);
		} catch (Exception e) {
			return badrequest(response, null);
		}
        try {
            Actor actor= actorService.findActorByPrincipal();
            principal = (User) actor;
        } catch (Exception e) {
            return unauthorized(response, null);
        }
		try {
			return teamService.createTeamForm(team);
		} catch (Throwable e) {
			return internalservererror(response, e.getMessage());
		}
	}

    @RequestMapping(value= "/team/{team}/invite",method = RequestMethod.POST)
    public Object editTeam(@PathVariable("team") Team team, Collection<User> users, HttpServletResponse response){
        User principal;
        try{
            Assert.notNull(users);
            Assert.notNull(team);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(team.getLeader().equals(principal));
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            teamService.invite(users,team);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }



    @RequestMapping(value= "/team/{team}/edit",method = RequestMethod.POST)
	public Object editTeam(@PathVariable("team") Team team, TeamForm teamForm, HttpServletResponse response){
        User principal;
        try{
            Assert.notNull(teamForm);
            Assert.notNull(team);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(team.getLeader().equals(principal));
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            teamService.edit(teamForm,team);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }

    @RequestMapping(value= "/team/{team}/leader/{user}")
    public Object promoteLeader(@PathVariable("team") Team team, @PathVariable("user") User user, HttpServletResponse response){
        User principal;
        try{;
            Assert.notNull(team);
            Assert.notNull(user);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            Assert.isTrue(team.getLeader().equals(principal));
            Assert.isTrue(team.getUsers().contains(user));
            Assert.isTrue(!(user.equals(principal)));
        }catch (Exception e){
            return badrequest(response,null);
        }
        try{
            teamService.promoteLeader(team,user);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }


    @RequestMapping(value= "/team/{team}/delete")
    public Object deleteTeam(@PathVariable("team") Team team, HttpServletResponse response){
        User principal;
        try{;
            Assert.notNull(team);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(team.getLeader().equals(principal));
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            teamService.delete(team);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,e.getMessage());
        }
    }

    @RequestMapping(value= "/team/{team}/join")
    public Object enterTeamByPassword(@PathVariable("team") Team team,
                                      @RequestParam(value = "password", required = true) String password,
                                      HttpServletResponse response){
        User principal;
        try{;
            Assert.notNull(team);
            Assert.notNull(password);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(!(team.getUsers().contains(principal)));
            Assert.isTrue(team.getPassword().equals(password));
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            teamService.enterByPassword(team,password);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }
    @RequestMapping(value= "/team/{team}/kick/{user}")
    public Object kickMember(@PathVariable("team") Team team, @PathVariable("user") User user,
                             HttpServletResponse response){
        User principal;
        try{
            Assert.notNull(team);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(!(user.equals(principal)));
            Assert.isTrue((team.getLeader().equals(principal)));
            Assert.isTrue(team.getUsers().contains(user));
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            teamService.kickMemberTeam(team, user);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }

    @RequestMapping(value= "/team/{team}/leave")
    public Object leaveTeam(@PathVariable("team") Team team, HttpServletResponse response){
        User principal;
        try{
            Assert.notNull(team);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            principal = userService.findByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(!(team.getLeader().equals(principal)));
            Assert.isTrue(team.getUsers().contains(principal));
        } catch (Exception e){
            return  unauthorized(response,null);
        }
        try{
            teamService.leaveTeam(team);

            return  ok(response,null);
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }

	@RequestMapping(value = "/team/{team}")
	public Object get(@PathVariable Team team, HttpServletRequest request, HttpServletResponse response) {
		try {
			Assert.notNull(team);
		} catch (Exception e) {
			return badrequest(response, null);
		}

		try {
			Map<String,Object> map = new HashMap<>();
			map.put("team",team);
			map.put("tournaments",team.getTournaments());

			return map;
		} catch (Throwable e) {
			return internalservererror(response, null);
		}
	}
}

