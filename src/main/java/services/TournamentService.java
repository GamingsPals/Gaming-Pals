
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Award;
import domain.Confrontation;
import domain.Participes;
import domain.Team;
import domain.Tournament;
import forms.TournamentForm;
import repositories.TournamentRepository;

@Service
@Transactional
public class TournamentService {

	@Autowired
	private TournamentRepository	tournamentRepository;

	@Autowired
	private ParticipesService		participesService;

	@Autowired
	private TeamService				teamService;


	public TournamentService() {
		super();
	}

	public Tournament create() {

		Tournament result = new Tournament();
		result.setMomentCreate(new Date(System.currentTimeMillis() - 1000));
		result.setAwards(new ArrayList<Award>());
		result.setConfrontations(new ArrayList<Confrontation>());
		result.setTeams(new ArrayList<Team>());

		return result;
	}

	public Tournament save(Tournament tournament) {

		Assert.notNull(tournament);
		return tournamentRepository.save(tournament);

	}

	public void delete(Tournament tournament) {

		Assert.notNull(tournament);
		tournamentRepository.delete(tournament);

	}

	public Tournament findOne(int tournamentId) {

		Tournament result = tournamentRepository.findOne(tournamentId);

		Assert.notNull(result);

		return result;

	}

	public Collection<Tournament> findAll() {

		Collection<Tournament> result;
		result = tournamentRepository.findAll();

		Assert.notNull(result);

		return result;

	}

	public Tournament reconstruct(TournamentForm tournamentForm) {
		Tournament t = create();
		t.setTitle(tournamentForm.getTitle());
		t.setDescription(tournamentForm.getDescription());
		t.setRules(tournamentForm.getRules());
		t.setNumberTeams(tournamentForm.getNumberTeams());
		t.setLimitInscription(tournamentForm.getLimitInscription());
		t.setPicture(tournamentForm.getPicture());
		return t;
	}

	public void assign(Team team, Tournament t) {
		Assert.isTrue(t.getTeams().size() < t.getNumberTeams());
		t.getTeams().add(team);
		team.getTournaments().add(t);
		Participes p = participesService.create();
		p.setTeam(team);
		p = participesService.save(p);
		for (Confrontation c : t.getConfrontations()) {
			if (c.getRound() == 1 && !c.getParticipes().contains(p) && c.getParticipes().size() < 2) {
				c.getParticipes().add(p);
				break;
			}
		}
		participesService.save(p);
		teamService.save(team);
		save(t);
	}

    public void advanceRound(Tournament tournamentId) {
        //Recorro todas las confrontation del torneo
			for (Confrontation c : tournamentId.getConfrontations()) {
				System.out.println("----------------------");
				//Compruebo que no sea la última ronda
				boolean nonStop = (tournamentId.getNumberTeams() / (Math.pow(2, c.getRound()))) != 1;
				if(!nonStop){
					break;
				}
				//Preparo el número de enfrentamiento en la siguiente ronda (Depende de si es par o impar)
				int nexMatch = 1;
				if (c.getNumberMatch() % 2 != 0) {
					double aux = (c.getNumberMatch() / 2) + 1;
					nexMatch = (int) aux;
				} else {
					double aux = c.getNumberMatch() / 2;
					nexMatch = (int) aux;
				}
				//Con el numberMatch y la round busco la confrontation a la que avanzará el equipo
				Confrontation nextRound = tournamentRepository.findByRoundAndMatch(c.getRound() + 1, nexMatch, tournamentId.getId());
				boolean canAdvance = true;
				//Comprobamos que el limite de jugar ya haya pasado y que el de la siguiente ronda aun no
				if (c.getLimitPlay().before(new Date()) && nextRound.getLimitPlay().after(new Date())) {
					//Recorremos los Participes del Confrontation, y viendo el ganador lo metemos en la siguiente ronda. O si solo hay un equipo en esa ronda.
					for (Participes p : c.getParticipes()) {
						if (p.getIsWinner() || c.getParticipes().size() < 2) {
							//Recorremos los particpes de la ronda siguiente para comprobar si ya ha pasado ese equipo
							//Si el equipo está en la siguiente ronda, paramos le bucle, si no, lo dejamos como true para comprobar el otro participe
							for(Participes pN:nextRound.getParticipes()){
								if(pN.getTeam().equals(p.getTeam())){
									canAdvance = false;
									break;
								}else{
									canAdvance = true;
								}
							}
							if(canAdvance){
								Participes pAux = participesService.create();
								pAux.setTeam(p.getTeam());
								pAux = participesService.save(pAux);
								nextRound.getParticipes().add(pAux);
								break;
							}
						}
					}
				}
			}
    }

}
