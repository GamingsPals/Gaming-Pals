
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Report;
import repositories.ReportRepository;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReportRepository reportRepository;

	@Autowired
    private UserService userService;
	// Supporting services ----------------------------------------------------


	// Constructors -----------------------------------------------------------
	public ReportService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public Report create() {
		Report res = new Report();
		return res;
	}
	public Collection<Report> findAll() {
		Collection<Report> res = reportRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	public Report findOne(int reportId) {
		return reportRepository.findOne(reportId);
	}
	public Report save(Report report) {
		Assert.notNull(report);
		return reportRepository.save(report);
	}
	public void delete(Report report) {
		reportRepository.delete(report);
	}

    public void report(User user,Report report) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(user);
		Assert.notNull(report);
        report.setReportedUser(user);
        report.setReporterUser(u);

		save(report);

    }
    // Other business methods -------------------------------------------------

}
