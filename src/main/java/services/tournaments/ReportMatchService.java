package services.tournaments;

import domain.*;
import forms.ReportMatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportMatchRepository;
import services.AdministratorService;
import services.UserService;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ReportMatchService {    //Repositories
    @Autowired
    private ReportMatchRepository reportMatchRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfrontationService confrontationService;

    @Autowired
    private ParticipesService participesService;

    @Autowired
    private AdministratorService administratorService;
    //Services

    //Constructor
    public ReportMatchService(){super();}

    //CRUD METHODS
    public ReportMatch create(){
        ReportMatch result = new ReportMatch();

        return result;
    }

    public ReportMatch save(ReportMatch reportMatch) {

        Assert.notNull(reportMatch);
        return reportMatchRepository.save(reportMatch);

    }

    public void delete(ReportMatch reportMatch) {

        Assert.notNull(reportMatch);
        reportMatchRepository.delete(reportMatch);

    }

    public ReportMatch findOne(int reportMatchId) {

        ReportMatch result=reportMatchRepository.findOne(reportMatchId);

        Assert.notNull(result);

        return result;

    }

    public Collection<ReportMatch> findAll() {

        Collection<ReportMatch> result;
        result = reportMatchRepository.findAll();

        Assert.notNull(result);

        return result;

    }

    //Other business metthods
    public ReportMatch reconstruct(ReportMatchForm reportMatchForm){
        ReportMatch r = create();
        r.setImage(reportMatchForm.getImage());
        r.setResult(reportMatchForm.getResult());
        r.setDescription(reportMatchForm.getDescription());
        return r;
    }

    public void reportConfrontation(ReportMatch reportMatch, Team team, Confrontation confrontation){
        Assert.notNull(reportMatch);
        Assert.notNull(team);
        Assert.notNull(confrontation);
        Assert.isTrue(team.getUsers().contains(userService.findByPrincipal()));
        confrontation.getReportMatches().add(reportMatch);
        Confrontation c = confrontationService.save(confrontation);
        reportMatch.setConfrontation(c);
        reportMatch.setTeam(team);
        Participes p = reportMatchRepository.findParticipeByTeamId(team.getId(),reportMatch.getConfrontation());
        Assert.notNull(p);
        Boolean report = false;
        switch (reportMatch.getResult()){
            case "Winner":
                report = true;
                break;
            case "Incidence":
                report = null;
                break;
            case "Looser":
                report = false;
                break;
        }
        //Comprobamos si los dos equipos han seleccionado el mismo resultado
        if(report!=null) {
            if (checkTwoParticipesSameResult(reportMatch,report,p)){
                twoWinnerIncidence(c);
            }else{
            p.setWinner(report);
            for (Participes par : c.getParticipes()) {
                if (par.getId() != p.getId()) {
                    par.setWinner(!report);
                }
            }
        }}
        ReportMatch r = save(reportMatch);

    }

    private Boolean checkTwoParticipesSameResult(ReportMatch reportMatch, Boolean currentResult, Participes p) {
        Assert.notNull(reportMatch);
        Boolean result = false;
        for(ReportMatch rp: reportMatch.getConfrontation().getReportMatches()){
            if(reportMatch.getResult().equals(rp.getResult())
                    && !reportMatch.getResult().equals("Incidence") && !rp.getTeam().equals(p.getTeam())){
                result = true;
            }
        }

        return result;
    }



    public void twoWinnerIncidence(Confrontation c) {
        Assert.notNull(c);
        ReportMatch reportMatch = new ReportMatch();
        reportMatch.setConfrontation(c);
        reportMatch.setImage("http://www.hbc333.com/data/out/228/46719779-tumblr-pictures.gif");
        reportMatch.setDescription("There are two teams that set the result of the same match with the same result");
        reportMatch.setResult("Incidence");
        save(reportMatch);
    }

    public Collection<ReportMatch> activeIncidencesByTournament(Tournament tournament) {
        Assert.notNull(tournament);

        return reportMatchRepository.activeIncidencesByTournament(tournament);
    }
}
