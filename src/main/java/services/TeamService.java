package services;

import domain.Team;
import domain.Tournament;
import domain.User;
import forms.TeamForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TeamRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class TeamService {
    //Repositories
    @Autowired
    private TeamRepository teamRepository;

    //Services
    @Autowired
	private Validator validator;

    //Constructor
    public TeamService(){super();}

    //CRUD METHODS
    public Team create(){
        Team result = new Team();
        result.setUsers(new ArrayList<User>());
        result.setTournaments(new ArrayList<Tournament>());

        return result;
    }

    public Team save(Team team) {

        Assert.notNull(team);
        return teamRepository.save(team);

    }

    public void delete(Team team) {

        Assert.notNull(team);
        teamRepository.delete(team);

    }

    public Team findOne(int teamId) {

        Team result=teamRepository.findOne(teamId);

        Assert.notNull(result);

        return result;

    }

    public Collection<Team> findAll() {

        Collection<Team> result;
        result = teamRepository.findAll();

        Assert.notNull(result);

        return result;

    }
    
    public Team reconstruct(final TeamForm teamForm, final BindingResult binding) {
		final Team team = this.create();
		team.setName(teamForm.getName());
		team.setPicture(teamForm.getPicture());
		this.validator.validate(team, binding);
		return team;
	}
}
