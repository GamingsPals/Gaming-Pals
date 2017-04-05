
package controllers.API;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import services.ActorService;
import services.ConfrontationService;
import services.TeamService;
import services.TournamentService;

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


	@RequestMapping(value = "/tournament/assign/{tournamentId}/{teamId}")
	public Object confrontationList(HttpServletRequest request, HttpServletResponse response, @PathVariable int tournamentId, @PathVariable int teamId) {
		try {
			Assert.notNull(tournamentService.findOne(tournamentId));
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {
			Tournament t = tournamentService.findOne(tournamentId);
			Team team = teamService.findOne(teamId);

			tournamentService.assign(team, t);
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
			t = tournamentService.save(t);
			t = confrontationService.calculateConfrontations(t);

			return ok(response, null);
		} catch (Exception e) {

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
}
