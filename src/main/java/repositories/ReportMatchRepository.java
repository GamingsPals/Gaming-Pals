
package repositories;

import domain.Confrontation;
import domain.Participes;
import domain.ReportMatch;
import domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReportMatchRepository extends JpaRepository<ReportMatch, Integer> {

    @Query("select p from Confrontation c join c.participes p where p.team.id=?1 and c=?2")
    Participes findParticipeByTeamId(int id, Confrontation confrontation);

    @Query("select r from Tournament  t join t.confrontations c join c.reportMatches r where t=?1 and (c.participes.size>0 and c.played = false) and r.revised=false and r.result='Incidence'")
    Collection<ReportMatch> activeIncidencesByTournament(Tournament tournament);
}
