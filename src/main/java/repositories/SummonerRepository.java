
package repositories;

import domain.League;
import domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, Integer> {


    @Query("select s from Summoner s where s.username=?1 and s.region=?2")
    Summoner findOneByName(String name, String region);
}
