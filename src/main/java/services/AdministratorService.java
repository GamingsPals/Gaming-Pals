
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;


	// Constructor
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = administratorRepository.findAll();
		return result;
	}

	public Administrator findOne(int id) {
		Administrator result;
		result = administratorRepository.findOne(id);
		return result;
	}

	public void checkIsAdmin() {
		Authority aut = new Authority();
		aut.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(aut));
	}

}
