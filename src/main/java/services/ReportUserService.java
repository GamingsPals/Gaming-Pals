
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.ReportUser;
import forms.ReportUserForm;
import repositories.ReportUserRepository;

@Service
@Transactional
public class ReportUserService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReportUserRepository	reportUserRepository;
	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService				userService;


	// Constructors -----------------------------------------------------------
	public ReportUserService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public ReportUser create() {
		ReportUser res = new ReportUser();
		res.setUserReporter(userService.findByPrincipal());
		return res;
	}
	public Collection<ReportUser> findAll() {
		Collection<ReportUser> res = reportUserRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	public ReportUser findOne(int reportUserId) {
		return reportUserRepository.findOne(reportUserId);
	}
	public ReportUser save(ReportUser reportUser) {
		Assert.notNull(reportUser);
		return reportUserRepository.save(reportUser);
	}
	public void delete(ReportUser reportUser) {
		reportUserRepository.delete(reportUser);
	}
	// Other business methods -------------------------------------------------

	public ReportUser reconstruct(ReportUserForm reportUserForm){
		ReportUser reportUser = create();
		
		reportUser.setComment(reportUserForm.getComment());
		reportUser.setPicture(reportUserForm.getPicture());
		
		return reportUser;
	}
}
