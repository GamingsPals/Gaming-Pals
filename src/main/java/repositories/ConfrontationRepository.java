
package repositories;

import domain.Confrontation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfrontationRepository extends JpaRepository<Confrontation, Integer> {

}
