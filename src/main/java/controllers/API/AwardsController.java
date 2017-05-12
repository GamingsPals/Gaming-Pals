
package controllers.API;

import javax.servlet.http.HttpServletResponse;

import domain.Award;
import forms.AwardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import domain.Tournament;
import services.AdministratorService;
import services.AwardService;
import services.TournamentService;

@RestController
@RequestMapping("/api")
public class AwardsController extends ApiAbstractController {

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private AwardService awardService;

	@Autowired
	private AdministratorService administratorService;

	//List
	@RequestMapping(value = "/awards/tournament/list")
	public Object awardsList(HttpServletResponse response, @RequestParam int tournamentId) throws Exception {
		try {
			Assert.notNull(tournamentService.findOne(tournamentId));
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {
			Tournament t = tournamentService.findOne(tournamentId);
			return t.getAwards();
		} catch (Exception e) {
			return internalservererror(response, null);
		}
	}

	//Create
	@RequestMapping(value = "/awards/{tournament}/create",method = RequestMethod.POST)
	public Object create(@PathVariable Tournament tournament, AwardForm awardForm, HttpServletResponse response){
		try{
			Assert.notNull(tournament);
		}catch (Exception e){
			return notFoundError(response,null);
		}
		try{
			Assert.notNull(awardForm);
		}catch (Exception e){
			return notFoundError(response,null);
		}
		try{
			administratorService.checkIsAdmin();
		}catch (Exception e){
			return unauthorized(response,null);
		}
		try{
			Award award = awardService.reconstruct(awardForm);
			award.setTournament(tournament);
			return ok(response, null);
		}catch (Exception e){
			return internalservererror(response,null);
		}
	}
}
