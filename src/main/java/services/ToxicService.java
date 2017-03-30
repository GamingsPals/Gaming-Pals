package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Toxic;

import repositories.ToxicRepository;
import security.Authority;

@Service
@Transactional
public class ToxicService {

	// Managed Repository
	@Autowired
	private ToxicRepository toxicRepository;

	// Supporting Services
	@Autowired
	private ActorService actorService;

	// Constructor
	public ToxicService() {
		super();
	}

	// Simple CRUD methods
	public Toxic create() {
		Toxic result = new Toxic();
		return result;
	}
	
	public Collection<Toxic> findAll() {
		Collection<Toxic> result;
		Assert.notNull(toxicRepository);
		result = toxicRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Toxic findOne(int toxicId) {
		return toxicRepository.findOne(toxicId);

	}

	public Toxic save(Toxic toxic) {
		Toxic result;
		result = toxicRepository.save(toxic);
		return result;
	}
	
	public void delete(Toxic toxic) {
		Assert.notNull(toxic);
		Assert.isTrue(toxic.getId() != 0);
		toxicRepository.delete(toxic);
	}

	// Other business methods
	public Toxic registerToxic(String keyWord) {
		Actor actor = actorService.findActorByPrincipal();
		Assert.notNull(actor);
		Toxic result = new Toxic();
		for (Authority a : actor.getUserAccount().getAuthorities()) {
			if (a.getAuthority().equals("ADMIN")) {
				result = toxicRepository.findAll().get(0);
				result.getKeywords().add(keyWord);
				save(result);
			}
		}
		return result;
	}

	public Collection<String> listKeyWord() {
		return toxicRepository.findAll().get(0).getKeywords();
	}

}
