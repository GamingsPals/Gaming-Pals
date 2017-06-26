
package repositories;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import security.UserAccount;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	@Query("select a from Actor a where a.userAccount.username=?1")
	Actor findActorByUsername(String username);

	@Query("select a from Actor a where a.userAccount = ?1 ")
	Actor findActorByUserAccount(UserAccount userAccount);

	@Query("select a from Actor a where lower(a.userAccount.username)=?1")
	Actor findByUserAccountUsername(String string);

	@Query("select a from Actor a where lower(a.email)=?1")
	Actor findByEmail(String s);
}
