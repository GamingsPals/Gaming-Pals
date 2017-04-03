
package controllers.API;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import domain.Tournament;
import services.TournamentService;

@RestController
@RequestMapping("/api")
public class AwardsController extends ApiAbstractController {

	@Autowired
	private TournamentService tournamentService;


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
}
