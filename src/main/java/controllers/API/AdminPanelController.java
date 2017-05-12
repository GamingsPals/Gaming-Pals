
package controllers.API;

import domain.Administrator;
import domain.Game;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.ActorService;
import services.AdministratorService;
import services.GameService;
import services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AdminPanelController extends ApiAbstractController {

	@Autowired
	private GameService gameService;


	@Autowired
	private UserService userService;

	@Autowired
	private AdministratorService administratorService;


	public Object banPlayer(User user,HttpServletResponse response){
		Administrator administrator;
		try{
			administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
		} catch (Exception e){
			return unauthorized(response,null);
		}
		try{
			Assert.notNull(user);
		} catch (Exception e){
			return badrequest(response,null);
		}
		try{
			Boolean ban = !user.getUserAccount().getLocked();
			user.getUserAccount().setLocked(ban);
			userService.save(user);

			return ok(response,null);
		} catch (Exception e){
			return internalservererror(response,null);
		}

	}
}
