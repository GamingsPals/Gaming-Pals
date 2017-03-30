package repositories;

import domain.Administrator;
import domain.Game;
import domain.Summoner;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
	@Query(" select g from Game g where g.tag =?1")
	public Game getGameByTag(String tag);


	@Query("select s from Summoner s where s.user=?1")
	public Summoner getSummonerByUser(User user);
}
