
package repositories;

import domain.Participes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipesRepository extends JpaRepository<Participes, Integer> {

}
