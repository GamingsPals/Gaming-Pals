package services;

import domain.Report;
import domain.User;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ReportRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserService userService;


    public List<Report> findAll(){
        try{
            return reportRepository.findAllReports();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public void report(User user, Report report) {
        Assert.notNull(report);
        Assert.notNull(user);
        User reporterUser = userService.findByPrincipal();
        Assert.notNull(reporterUser);
        Report report1 = new Report();
        report1.setComment(report.getComment());
        report1.setPicture(report.getPicture());
        report1.setReporterUser(reporterUser);
        report1.setReportedUser(user);

        save(report1);
    }

    private Report save(Report report1) {
        Assert.notNull(report1);

        return reportRepository.save(report1);
    }

    public void delete(Report report) {
        Assert.notNull(report);

        reportRepository.delete(report);
    }
}
