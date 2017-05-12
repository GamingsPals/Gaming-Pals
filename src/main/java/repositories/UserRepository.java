package repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u order by u.ratingAvg DESC")
    List<User> findBestRanked();	
	
	@Query("select u from User u where u.userAccount.id=?1")
	User findByPrincipal(int userId);

	@Query("select u from User u where u.userAccount.username like ?1")
	Collection<User> usersByName(String name);
	
	@Query("select u from User u join u.gameInfo gi join gi.game g where g.tag = ?1")
	 Collection<User> usersForGameTag(String tag);
	
	@Query("select u from User u join u.gameInfo gi join gi.game g order by g.tag")
	Collection<User> usersForGameTag();
	
	@Query("select s.user from League l join l.summoner s join s.game g where g.tag = ?1 and l.tier = ?2")
	Collection<User> usersFromGameAndTier(String gameTag, String tier);
	
	@Query("select u from User u join u.languages l order by l.language")
	Collection<User> usersForLanguage();

	@Query("select u from User u join u.languages l where l.language=?1")
	Collection<User> usersForLanguage(String language);

	@Query("select u from Game g join g.gameInfos gi join gi.user u join u.userAccount ua where g.tag = ?1 and ua.username = ?2")
	Collection<User> userFromUsernameAndTagGame();

	
	@Query("select u from User u where u.userAccount.username=?1")
    User findByUserAccountUsername(String string);

	@Query("select u from User u where u.email=?1")
    User findUserByEmail(String email);

	@Query("select u from User u where u.userAccount.locked=true")
    Collection<User> findAllNotBanned();
}
