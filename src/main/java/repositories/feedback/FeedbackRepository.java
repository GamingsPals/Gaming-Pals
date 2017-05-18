
package repositories.feedback;

import domain.Actor;
import domain.Configuration;
import domain.feedback.Feedback;
import domain.feedback.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {


    @Query("select f from Feedback f where f.parent is null")
    Collection<Feedback> findAllParents();

    @Query("select l from Feedback  f join f.likes l where l.author=?2 and f=?1")
    Likes findLikeByRepositoryAndUser(Feedback feedback, Actor actor);
}
