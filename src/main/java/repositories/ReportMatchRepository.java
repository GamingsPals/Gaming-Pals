
package repositories;

import domain.ReportMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMatchRepository extends JpaRepository<ReportMatch, Integer> {

}
