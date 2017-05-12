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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import forms.TeamForm;
import services.ActorService;
import services.notifications.TeamInvitationNotificationService;
import services.TeamService;
import services.UserService;

@Controller
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

    @ResponseBody
	@RequestMapping(value = "/team/create", method = RequestMethod.POST)
	public Object create(TeamForm team, HttpServletRequest request, HttpServletResponse response, BindingResult binding) {
        User principal;
		try {
			System.out.println("null");
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
			System.out.println(e.getMessage());
			return internalservererror(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/team/{name}")
	public Object get(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
		try {
			Assert.notNull(name);
		} catch (Exception e) {
			return badrequest(response, null);
		}

		try {
			Team t = teamService.findByName(name);
			Assert.notNull(t);

			return t;
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			return internalservererror(response, null);
		}
	}
}

