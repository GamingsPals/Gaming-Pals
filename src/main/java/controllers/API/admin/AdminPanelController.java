
package controllers.API.admin;

import controllers.API.ApiAbstractController;
import domain.Administrator;
import domain.Game;
import domain.User;
import forms.SteamGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
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

	@Autowired
    private SteamService steamService;

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
			Boolean ban = !user.getUserAccount().getBanned();
			user.getUserAccount().setBanned(ban);
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

	@RequestMapping("/admin/games/steam/all")
	public Object getGamesSteam(@RequestParam("search") String search, HttpServletResponse response){
		try{
			Administrator administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
		} catch (Exception e) {
			return unauthorized(response,null);
		}
		try{
			return steamService.filteredGamesSteam(search);
		} catch (Exception e){
			return internalservererror(response);
		}
	}

	@RequestMapping(value = "/admin/games/steam/add",method = RequestMethod.POST)
	public Object addGameSteam(SteamGame steamGame, HttpServletResponse response){
		try{
			Administrator administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
		} catch (Exception e) {
			return unauthorized(response,null);
		}
		try{
			Assert.notNull(steamGame);
		} catch (Exception e){
			return badrequest(response);
		}
		try{
			gameService.addGame(steamGame);

			return  ok(response);
		} catch (Exception e){
			return internalservererror(response);
		}
	}
}
