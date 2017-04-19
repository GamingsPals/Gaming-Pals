
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;

	@Autowired
	LoginService loginService;

	@Autowired
	private Validator validator;


	// Constructor
	public ActorService() {
		super();
	}

	// Simple CRUD methods 

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = actorRepository.findAll();
		return result;
	}
	public Actor findOne(int id) {
		Actor result;
		result = actorRepository.findOne(id);
		return result;
	}

	// Other business methods 
	public Actor getLoggedActor() {
		Actor result;
		UserAccount user;
		if (isAuthenticated() == true) {
			user = LoginService.getPrincipal();
			result = actorRepository.findActorByUsername(user.getUsername());
		} else {
			result = null;
		}
		return result;
	}

	public Boolean isAuthenticated() {
		Boolean res = true;
		try {
			LoginService.getPrincipal();
		} catch (Exception e) {
			res = false;
		}

		return res;
	}

	public Actor findActorByUsername(String username) {
		return actorRepository.findActorByUsername(username);
	}

	public Actor save(Actor a) {
		return actorRepository.save(a);
	}

	public Actor edit(Actor actor) {
		Assert.notNull(actor);
		Actor logged = getLoggedActor();
		Assert.isTrue(actor.equals(logged));
		return save(actor);
	}

	public void checkPrincipal(Actor actor1, Actor actor2) {
		Assert.isTrue(actor1.getId() == actor2.getId());
	}
	public void checkIsLogged() {
		Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		Collection<Authority> auts2 = new ArrayList<Authority>();
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Authority b = new Authority();
		b.setAuthority(Authority.USER);
		Authority c = new Authority();
		c.setAuthority(Authority.MODERATOR);
	
		auts2.add(a);
		auts2.add(b);
		auts2.add(c);

		Assert.isTrue(!auts.contains(auts2));
	}

	public Actor findActorByPrincipal() {
		UserAccount userAccount = LoginService.getPrincipal();
		return actorRepository.findActorByUserAccount(userAccount);
	}

    public Actor findByUserAccountUsername(String actor2) {
		return actorRepository.findByUserAccountUsername(actor2);
    }
}
