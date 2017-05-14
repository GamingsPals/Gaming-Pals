package repositories;

import domain.Game;
import domain.GameInfo;
import domain.Summoner;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Integer> {

}
