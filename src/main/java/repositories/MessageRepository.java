package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	@Query("select m from Message m where m.receiver.id =?1")
	public Collection<Message> messagesByUser(int userId);
	
	@Query("select m from Message m where (m.toxic='false' AND m.receiver.id =?1)")
	public Collection<Message> safetyMessagesByUser(int userId);
	
	@Query("select m from Message m where (m.toxic='true' AND m.receiver.id =?1)")
	public Collection<Message> unsafetyMessagesByUser(int userId);

}
