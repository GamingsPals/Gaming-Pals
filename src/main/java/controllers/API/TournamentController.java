package controllers.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.TournamentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class TournamentController extends ApiAbstractController{
    //Services
    @Autowired
    private TournamentService tournamentService;

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
