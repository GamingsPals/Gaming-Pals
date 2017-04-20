
package repositories;

import domain.Confrontation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

    @Query("select c from Confrontation c where c.round=?1 AND(c.numberMatch=?2) AND(c.tournament.id=?3)")
    Confrontation findByRoundAndMatch(int round, int numberMatch, int tournamentId);
}
