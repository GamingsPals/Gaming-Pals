
package controllers.API;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Tournament;
import forms.TournamentForm;
import services.ConfrontationService;
import services.TournamentService;

@Controller
@RequestMapping("/api")
public class TournamentController extends ApiAbstractController {

	@Autowired
	private TournamentService		tournamentService;
	@Autowired
	private ConfrontationService	confrontationService;


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
    public Object tournamentList(HttpServletResponse response)
            throws Exception{
        try{
            return tournamentService.findAll();
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }
}
