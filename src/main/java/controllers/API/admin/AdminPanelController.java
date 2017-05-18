
package controllers.API.admin;

import controllers.API.ApiAbstractController;
import domain.Administrator;
import domain.Game;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import services.*;

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

	@Autowired
	private ReportService reportService;

	@ResponseBody
	@RequestMapping(value = "/admin/ban/{user}")
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

	@ResponseBody
	@RequestMapping(value = "/admin/ban/list")
	public Object banedUsers(HttpServletResponse response){
		Administrator administrator;
		try{
			administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
		} catch (Exception e){
			return unauthorized(response,null);
		}
		try{

			return userService.findAllNotBanned();
		} catch (Exception e){
			return internalservererror(response,null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/admin/reports/list")
	public Object list(HttpServletResponse response) {
		try{
			Administrator administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
		} catch (Exception e) {
			return unauthorized(response,null);
		}
		try{
			return reportService.findAll();
		} catch (Exception e){
			System.out.println(e.getMessage());
			return internalservererror(response,null);
		}
	}
}
