
package controllers.API;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Actor;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import domain.Team;
import domain.Tournament;
import forms.TournamentForm;
import services.*;

@RestController
@RequestMapping("/api")
public class TournamentController extends ApiAbstractController {

	@Autowired
	private TournamentService		tournamentService;
	@Autowired
	private ConfrontationService	confrontationService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private TeamService				teamService;
	@Autowired
    private AdministratorService    administratorService;


	@RequestMapping(value = "/tournament/{tournament}")
	public Object get(Tournament tournament, HttpServletResponse response){
		Actor actor;
		try{
			actor = actorService.findActorByPrincipal();
			Assert.notNull(actor);
		} catch (Exception e){
			return unauthorized(response,null);
		}
		try{
			Assert.notNull(tournament);
		} catch (Exception e){
			return notFoundError(response,null);
		}
		try{
			return tournament;
		} catch (Exception e){
			return internalservererror(response,null);
		}
	}

	@RequestMapping(value = "/tournament/assign/{tournamentId}/{teamId}")
	public Object confrontationList( HttpServletResponse response,
                                    @PathVariable Tournament tournamentId, @PathVariable Team teamId) {
	    User principal;
		try {
			principal = (User) actorService.findActorByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(teamService.isUserInTeam(teamId,principal));
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {

			tournamentService.assign(teamId, tournamentId);
			return ok(response, null);
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			return internalservererror(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/createTournament", method = RequestMethod.POST)
	public Object register(TournamentForm tournamentForm, HttpServletRequest request, HttpServletResponse response, BindingResult binding) {
		try {
			Assert.notNull(tournamentForm);
		} catch (Exception e) {
			return notFoundError(response, null);
		}
		try {

			Tournament t = tournamentService.reconstruct(tournamentForm);
			System.out.println(t);
			t = tournamentService.save(t);
			t = confrontationService.calculateConfrontations(t);

			return ok(response, null);
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			return internalservererror(response, null);
		}
	}

	@RequestMapping(value = "/tournament/list")
	public Object tournamentList(HttpServletResponse response) throws Exception {
		try {
			Assert.notNull(actorService.findActorByPrincipal());
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {
			return tournamentService.findAll();
		} catch (Exception e) {
			return internalservererror(response, null);
		}
	}

	@ResponseBody
    @RequestMapping(value = "/tournament/advanceRound/{tournamentId}")
    public Object advanceRound(HttpServletResponse response, @PathVariable Tournament tournamentId){
	    System.out.println("Entra aqui");
	    try{
	        Assert.notNull(tournamentId);
        }catch (Exception e){
            return notFoundError(response,null);
        }try {
            administratorService.checkIsAdmin();
        }catch (Exception e){
            return unauthorized(response, null);
        }try{
            tournamentService.advanceRound(tournamentId);
            return ok(response, null);
        }catch(Exception e){
        	System.out.println(e.getMessage());
            return internalservererror(response,e.getMessage());
        }
    }
}
