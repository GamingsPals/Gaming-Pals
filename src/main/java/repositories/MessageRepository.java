package repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {


	@Query("select m.sender from Message m where m.receiver=?1 group by m.sender order by m.moment desc")
    Set<Actor> messagesReceived(Actor actor);

    @Query("select m.receiver from Message m where m.sender=?1 group by m.receiver order by m.moment desc")
    Set<Actor> messagesSended(Actor actor);

}
