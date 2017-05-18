package services.tournaments.admin;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import services.AdministratorService;
import services.tournaments.ReportMatchService;
import services.tournaments.TournamentService;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class TournamentAdminService {

    @Autowired
    private TournamentService tournamentService;


    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ReportMatchService reportMatchService;

    public Collection<ReportMatch> getActiveIncidencesByTournament(Tournament tournament) {
        Administrator administrator = administratorService.findByPrincipal();
        Assert.notNull(administrator);
        Assert.notNull(tournament);
        Collection<ReportMatch> reportMatches = reportMatchService.activeIncidencesByTournament(tournament);
        Assert.notNull(reportMatches);

        return reportMatches;
    }
    public void setWinnerConfrontation(Confrontation c, Team team){
        Assert.notNull(c);
        Assert.notNull(team);
        Administrator administrator = administratorService.findByPrincipal();
        Assert.notNull(administrator);
        for(Participes e: c.getParticipes()){
            if(e.getTeam().equals(team)){
                e.setPlayed(true);
                e.setWinner(true);
            }else{
                e.setPlayed(true);
                e.setWinner(false);
            }
        }
        updateIncidenceByConfrontation(c);
    }

    private void updateIncidenceByConfrontation(Confrontation c){
        Assert.notNull(c);
        for(ReportMatch reportMatch: c.getReportMatches()){
            reportMatch.setRevised(true);
            reportMatchService.save(reportMatch);
        }

    }

}
