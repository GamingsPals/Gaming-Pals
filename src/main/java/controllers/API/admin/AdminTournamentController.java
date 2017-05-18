package controllers.API.admin;

import controllers.API.ApiAbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.AdministratorService;
import services.tournaments.ConfrontationService;
import services.tournaments.ParticipesService;
import services.tournaments.ReportMatchService;
import services.tournaments.TournamentService;
import services.tournaments.admin.TournamentAdminService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminTournamentController extends ApiAbstractController {


    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private TournamentAdminService tournamentAdminService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private ConfrontationService confrontationService;

    @Autowired
    private ReportMatchService reportMatchService;

    @Autowired
    private ParticipesService participesService;


    @RequestMapping("/admin/tournament/{tournament}/incidences")
    public Object getIncidencesTournament(@PathVariable("tournament")Tournament tournament, HttpServletResponse response){
        Administrator administrator;
        try{
            administrator = administratorService.findByPrincipal();
            Assert.notNull(administrator);
        } catch (Exception e){
            return unauthorized(response);
        }
        try{
            Assert.notNull(tournament);
        } catch (Exception e){
            return badrequest(response);
        }
        try{
            Map<String,Object> result = new HashMap<>();
            Collection<ReportMatch> reportMatches = tournamentAdminService.getActiveIncidencesByTournament(tournament);
            return reportMatches;
        } catch (Exception e){
            return internalservererror(response);
        }
    }


    @RequestMapping("/admin/confrontation/{confrontation}")
    public Object changeConfrontationTime(@PathVariable("confrontation") Confrontation confrontation,
                                          @RequestParam("limitTime")Long limitTime,
                                          HttpServletResponse response){
        Administrator administrator;
        try{
            administrator = administratorService.findByPrincipal();
            Assert.notNull(administrator);
        } catch (Exception e){
            return unauthorized(response);
        }
        try{
            Assert.notNull(confrontation);
            Assert.notNull(limitTime);
        } catch (Exception e){
            return badrequest(response);
        }
        try{
            Date date = new Date(limitTime);
            confrontation.setLimitPlay(date);
            confrontationService.save(confrontation);

            return  ok(response);
        } catch (Exception e){
            return internalservererror(response);
        }
    }

    @RequestMapping("/admin/tournament/{tournament}/confrontation/{confrontation}/winner/{team}")
    public Object setWinnerConfrontation(@PathVariable("tournament") Tournament tournament,
                                         @PathVariable("confrontation")Confrontation confrontation,
                                         @PathVariable("team")Team team,
                                         HttpServletResponse response){
        Administrator administrator;
        try{
            administrator = administratorService.findByPrincipal();
            Assert.notNull(administrator);
        } catch (Exception e){
            return unauthorized(response);
        }
        try{
            Assert.notNull(tournament);
            Assert.notNull(team);
            Assert.notNull(confrontation);
            Assert.isTrue(tournament.getConfrontations().contains(confrontation));
            Boolean isTeamInConfrontation = false;
            for(Participes p:confrontation.getParticipes()){
                if(p.getTeam().equals(team)) {
                    isTeamInConfrontation = true;
                    break;
                }
            }
            Assert.isTrue(isTeamInConfrontation);
        } catch (Exception e){
            return badrequest(response);
        }
        try{
            tournamentAdminService.setWinnerConfrontation(confrontation,team);

            return ok(response);
        }catch (Exception e){
            return internalservererror(response);
        }
    }


}
