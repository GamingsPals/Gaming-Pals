
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {


    @Query("select t from Team t where t.name=?1")
    Team findByName(String name);
}
