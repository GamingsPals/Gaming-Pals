
package repositories;

import domain.SteamAccount;
import domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SteamAccountRepository extends JpaRepository<SteamAccount, Integer> {

}
