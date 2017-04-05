package services;

import domain.Confrontation;
import domain.ReportMatch;
import domain.Team;
import forms.ReportMatchForm;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ReportMatchRepository;

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

        ReportMatch r = save(reportMatch);
    }

}
