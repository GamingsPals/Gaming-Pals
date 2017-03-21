
package repositories;


import java.util.Collection;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("select r from Rating r where r.ratingUser=?1 and r.ratedUser=?2")
    Rating findRatingFromUserToUser(User u1, User u2);
}
