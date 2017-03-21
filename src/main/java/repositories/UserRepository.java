
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
	
	@Query("select u from User u where u.userAccount.id=?1")
	User findByPrincipal(int userId);
	
	/*HU007*/
	@Query("select u.name, u.surname, l.language from User u join u.languages l order by l")
	public String[] usersForLanguage();

	/*HU007*/
	@Query("select u.name, u.surname, l.language from User u join u.languages l where l.language=?1")
	public String[] usersForLanguage(String language);

	@Query("select u from User u order by ratingAvg DESC")
    List<User> findBestRanked();

	@Query("select u from User u where u.name=?1")
    User findByName(String text);
}
