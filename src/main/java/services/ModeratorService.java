package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Moderator;
import repositories.ModeratorRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class ModeratorService {
	
	@Autowired
	private ModeratorRepository	moderatorRepository;


	// Constructor
	public ModeratorService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Moderator> findAll() {
		Collection<Moderator> result;
		result = moderatorRepository.findAll();
		return result;
	}

	public Moderator findOne(int id) {
		Moderator result;
		result = moderatorRepository.findOne(id);
		return result;
	}

	public void checkIsAdmin() {
		Authority aut = new Authority();
		aut.setAuthority(Authority.MODERATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(aut));
	}


}
