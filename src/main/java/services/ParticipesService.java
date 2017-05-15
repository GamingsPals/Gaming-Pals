package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ParticipesRepository;
import repositories.TournamentRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ParticipesService {
    //Repositories
    @Autowired
    private ParticipesRepository participesRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserService userService;

    //Services

    //Constructor
    public ParticipesService(){super();}

    //CRUD METHODS
    public Participes create(){
        Participes result = new Participes();
        result.setIsWinner(false);

        return result;
    }

    public Participes save(Participes participes) {

        Assert.notNull(participes);
        return participesRepository.save(participes);

    }

    public void delete(Participes participes) {

        Assert.notNull(participes);
        participesRepository.delete(participes);

    }

    public Participes findOne(int participesId) {

        Participes result=participesRepository.findOne(participesId);

        Assert.notNull(result);

        return result;

    }

    public Collection<Participes> findAll() {

        Collection<Participes> result;
        result = participesRepository.findAll();

        Assert.notNull(result);

        return result;

    }
    public Map<String,Object> getConfrontation(Tournament tournamentId) {
        //Recorro todas las confrontation del torneo
        Map<String, Object> map = new HashMap<>();
        map.put("team",null);
        map.put("confrontation",null);
        User principal = userService.findByPrincipal();
        Assert.notNull(principal);
        for (Confrontation c : tournamentId.getConfrontations()) {
            boolean nonStop = (tournamentId.getNumberTeams() / (Math.pow(2, c.getRound()))) != 1;
            if(!nonStop){
                break;
            }
            int nexMatch = 1;
            if (c.getNumberMatch() % 2 != 0) {
                double aux = (c.getNumberMatch() / 2) + 1;
                nexMatch = (int) aux;
            } else {
                double aux = c.getNumberMatch() / 2;
                nexMatch = (int) aux;
            }
            Confrontation nextRound = tournamentRepository.findByRoundAndMatch(c.getRound() + 1, nexMatch, tournamentId.getId());
            boolean canAdvance = true;

                Boolean result = false;
                for (Participes p : c.getParticipes()) {
                    if (p.getIsWinner()) {
                        result = true;
                    }
                    if(result){
                        break;
                    }
                }
            Team selected = null;
                if(!result){
                    Collection<Team> teams = principal.getTeams();
                    if(c.getParticipes().size()==2) {
                        for (Participes p : c.getParticipes()) {
                            for (Team t : teams) {
                                if (t.getId() == p.getTeam().getId()) {
                                    selected = p.getTeam();
                                    break;
                                }
                            }
                            if (selected != null) {
                                break;
                            }
                        }
                    }
                }
            if(selected!=null){
                map.put("team", selected);
                map.put("confrontation", c);
                break;
            }
        }

        return map;
    }
}
