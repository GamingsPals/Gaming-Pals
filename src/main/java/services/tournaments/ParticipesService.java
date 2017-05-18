package services.tournaments;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ParticipesRepository;
import repositories.TournamentRepository;
import services.UserService;

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
        result.setWinner(false);

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
    public Map<String,Object> getConfrontation(Tournament tournament) {
        //Recorro todas las confrontation del torneo
        Assert.notNull(tournament);
        Map<String, Object> map = new HashMap<>();
        map.put("team",null);
        map.put("confrontation",null);
        User principal = userService.findByPrincipal();
        Assert.notNull(principal);
        Team userTeam = null;
        //Obtenemos el equipo en el que el principal está apuntado al torneo, sino devolvemos el mapa con todo null
        for(Team e: tournament.getTeams()){
            if(e.getUsers().contains(principal)){
                userTeam = e;
                break;
            }
        }
        if(userTeam==null) return map;
        Confrontation confrontationToReport = null;
        /*
            Recorro los confrontation buscando una en la que el equipo del principal este y NO esté jugada
            Compruebo si hay varias entonces que escoja la que mayor número de ronda tenga
         */
        for(Confrontation c: tournament.getConfrontations()){
            Boolean isConfrontationPlayed = false;
            for(Participes p: c.getParticipes()){
                if(p.isPlayed()){
                    isConfrontationPlayed = true;
                    break;
                }
            }

            // Compruebo que la confrontation no ha sido jugada y que el limite de juego es superior al actual
            if(!isConfrontationPlayed && c.getLimitPlay().after(new Date())) {
                for (Participes p : c.getParticipes()) {
                    if (p.getTeam().equals(userTeam) && !p.isPlayed()) {
                        if (confrontationToReport == null) {
                            confrontationToReport = c;
                        } else {
                            if (confrontationToReport.getRound() < c.getRound()) {
                                confrontationToReport = c;
                            }
                        }

                    }
                }
            }
        }

        if(confrontationToReport==null) return map;
        // Compruebo que el equipo reportador no ha reportado aún
        Boolean canReport = true;
        if(confrontationToReport.getReportMatches()!=null) {
                for (ReportMatch rp : confrontationToReport.getReportMatches()) {
                    if(rp.getTeam()!=null) {
                        if (rp.getTeam().equals(userTeam)) {
                            canReport = false;
                            break;
                        }
                    }
                }

        }
        if (!canReport) return  map;
        map.put("confrontation", confrontationToReport);
        map.put("team",userTeam);

        return map;
    }
}
