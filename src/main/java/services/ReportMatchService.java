package services;

import domain.ReportMatch;
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

}
