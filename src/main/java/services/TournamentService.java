package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Team;
import domain.Tournament;
import repositories.TournamentRepository;

@Service
@Transactional
public class TournamentService {
	
	@Autowired
 	private TournamentRepository	tournamentRepository;
 
 	public TournamentService() {
 		super();
 	}
 
 	public Tournament create() {
 
 		Tournament result = new Tournament();

 
 		return result;
 	}
 
 	public void save(Tournament tournament) {
 
 		Assert.notNull(tournament);
 		tournamentRepository.save(tournament);
 
 	}
 
 	public void delete(Tournament tournament) {
 
 		Assert.notNull(tournament);
 		tournamentRepository.delete(tournament);
 
 	}
 
 	public Tournament findOne(int tournamentId) {
 
 		Tournament result=tournamentRepository.findOne(tournamentId);
 
 		Assert.notNull(result);
 
 		return result;
 
 	}
 
 	public Collection<Tournament> findAll() {
 
 		Collection<Tournament> result;
 		result = tournamentRepository.findAll();
 
 		Assert.notNull(result);
 
 		return result;
 
 	}

}
