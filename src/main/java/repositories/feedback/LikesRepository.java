
package repositories.feedback;

import domain.Actor;
import domain.feedback.Feedback;
import domain.feedback.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {


}
