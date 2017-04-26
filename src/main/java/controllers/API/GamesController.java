
package controllers.API;

import domain.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.ActorService;
import services.GameService;
import services.TournamentService;
import services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class GamesController extends ApiAbstractController {

	@Autowired
	private GameService gameService;

	@Autowired
	private ActorService actorService;



	@RequestMapping(value = "/games/all")
	public Object allgames(HttpServletResponse response) throws Exception {
		try {
			Assert.notNull(actorService.findActorByPrincipal());
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {
			return gameService.findAll();
		} catch (Exception e) {
			return internalservererror(response, null);
		}
	}
}
