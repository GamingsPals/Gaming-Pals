package repositories;

import domain.Administrator;
import domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
	@Query(" select g from Game g where g.tag =?1")
	public Game getGameByTag(String tag);
}
