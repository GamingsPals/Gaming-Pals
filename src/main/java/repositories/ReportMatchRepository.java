
package repositories;

import domain.Confrontation;
import domain.Participes;
import domain.ReportMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMatchRepository extends JpaRepository<ReportMatch, Integer> {

    @Query("select p from Confrontation c join c.participes p where p.team.id=?1 and c=?2")
    Participes findParticipeByTeamId(int id, Confrontation confrontation);
}
