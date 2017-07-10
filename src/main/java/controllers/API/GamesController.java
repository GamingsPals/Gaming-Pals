
package controllers.API;

import domain.Actor;
import domain.Game;
import domain.GameInfo;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.ActorService;
import services.GameService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GamesController extends ApiAbstractController {

	@Autowired
	private GameService gameService;

	@Autowired
	private ActorService actorService;


	@RequestMapping(value = "/games/{game}")
	public Object get(@PathVariable("game")Game game,HttpServletResponse response){
		Actor actor;
		try{
			actor = actorService.findActorByPrincipal();
			Assert.notNull(actor);
		} catch (Exception e){
			return unauthorized(response);
		}
		try{
			Assert.notNull(game);
		} catch (Exception e){
			return badrequest(response);
		}
		try{
			Map<String,Object> result = new HashMap<>();
			result.put("game",game);
			List<User> users = new ArrayList<>();
			for(GameInfo g:game.getGameInfos()){
				users.add(g.getUser());
			}
			result.put("users",users);
			result.put("tournaments",game.getTournaments());
			return game;
		} catch (Exception e){
			return internalservererror(response);
		}
	}

	@RequestMapping(value = "/game/{game}")
	public Object game(@PathVariable("game")Game game,HttpServletResponse response){
		Actor actor;
		try{
			actor = actorService.findActorByPrincipal();
			Assert.notNull(actor);
		} catch (Exception e){
			return unauthorized(response);
		}
		try{
			Assert.notNull(game);
		} catch (Exception e){
			return badrequest(response);
		}
		try{
			Map<String,Object> result = new HashMap<>();
			result.put("game",game);
			List<User> users = new ArrayList<>();
			for(GameInfo g:game.getGameInfos()){
				users.add(g.getUser());
			}
			result.put("users",users);
			result.put("tournaments",game.getTournaments());
			return result;
		} catch (Exception e){
			return internalservererror(response);
		}
	}

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
