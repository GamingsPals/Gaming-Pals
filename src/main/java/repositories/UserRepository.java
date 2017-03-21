
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

	@Query("select u from User u order by ratingAvg DESC")
    List<User> findBestRanked();	
	
	@Query("select u from User u where u.userAccount.id=?1")
	User findByPrincipal(int userId);
	
	/*HU005*/
	@Query("select u from User u join u.gameInfo gi join gi.game g where g.tag = ?1")
	public Collection<User> usersForGameTag(String tag);
	
	/*HU005*/
	@Query("select u from User u join u.gameInfo gi join gi.game g order by g.tag")
	public Collection<User> usersForGameTag();
	
	/*HU006*/
	@Query("select u from User u join u.gameInfo gi join gi.leagues l where l.tier = ?1")
	public Collection<User> usersFromTier(String tier);
	
	/*HU007.1*/
	@Query("select u from User u join u.gameInfo gi join gi.leagues l order by l.tier")
	public Collection<User> usersFromTier();
	
	/*HU007*/
	@Query("select u from User u join u.languages l order by l")
	public Collection<User> usersForLanguage();

	/*HU007*/
	@Query("select u from User u join u.languages l where l.language=?1")
	public Collection<User> usersForLanguage(String language);

	/*HU006*/
	@Query("select u from Game g join g.gameInfos gi join gi.user u join u.userAccount ua where g.tag = ?1 and ua.username = ?2")
	public Collection<User> userFromUsernameAndTagGame();

	@Query("select u from User u where u.name=?1")
    public User findByName(String text);
}
