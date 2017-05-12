
package repositories;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import security.UserAccount;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    @Query("select a from Administrator  a where a.userAccount.id=?1")
    Administrator findByPrincipal(UserAccount id);
}
