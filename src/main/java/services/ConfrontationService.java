
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Confrontation;
import domain.Participes;
import domain.ReportMatch;
import domain.Tournament;
import repositories.ConfrontationRepository;

@Service
@Transactional
public class ConfrontationService {

	//Repositories
	@Autowired
	private ConfrontationRepository confrontationRepository;

	//Services


	//Constructor
	public ConfrontationService() {
		super();
	}

	//CRUD METHODS
	public Confrontation create() {
		Confrontation result = new Confrontation();
		result.setParticipes(new ArrayList<Participes>());
		result.setReportMatches(new ArrayList<ReportMatch>());

		return result;
	}

	public Confrontation save(Confrontation confrontation) {

		Assert.notNull(confrontation);
		return confrontationRepository.save(confrontation);

	}

	public void delete(Confrontation confrontation) {

		Assert.notNull(confrontation);
		confrontationRepository.delete(confrontation);

	}

	public Confrontation findOne(int confrontationId) {

		Confrontation result = confrontationRepository.findOne(confrontationId);

		Assert.notNull(result);

		return result;

	}

	public Collection<Confrontation> findAll() {

		Collection<Confrontation> result;
		result = confrontationRepository.findAll();

		Assert.notNull(result);

		return result;

	}

	public Tournament calculateConfrontations(Tournament tournament) {
		int ronda = 1;
		int confrontations = 0;

		while (confrontations != 1) {
			confrontations = (int) (tournament.getNumberTeams() / (Math.pow(2, ronda)));
			for (int i = 1; i <= confrontations; i++) {
				Confrontation c = create();
				c.setRound(ronda);
				c.setNumberMatch(i);
				Calendar cal = Calendar.getInstance();
				cal.setTime(tournament.getLimitInscription());
				cal.add(Calendar.HOUR, 24 * ronda);
				c.setLimitPlay(cal.getTime());
				tournament.getConfrontations().add(c);
				c.setTournament(tournament);
				save(c);
			}
			ronda++;

		}

		return tournament;
	}
}
