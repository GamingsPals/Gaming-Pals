
package repositories;

import domain.SteamAccount;
import domain.Summoner;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SteamAccountRepository extends JpaRepository<SteamAccount, Integer> {


    @Query("select s from SteamAccount s where s.user=?1")
    SteamAccount findByUser(User user);
}
