
package controllers.API;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import domain.Team;
import services.TeamService;

@RestController
@RequestMapping("/api")
public class UsersByTeamController extends ApiAbstractController {

	@Autowired
	private TeamService teamService;


	@RequestMapping(value = "/users/list")
	public Object awardsList(HttpServletResponse response, @RequestParam int teamId) throws Exception {
		try {
			Assert.notNull(teamService.findOne(teamId));
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {
			Team t = teamService.findOne(teamId);
			return t.getUsers();
		} catch (Exception e) {
			return internalservererror(response, null);
		}
	}
}
