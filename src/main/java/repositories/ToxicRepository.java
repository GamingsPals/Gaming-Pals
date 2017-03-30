package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Toxic;

@Repository
public interface ToxicRepository extends JpaRepository<Toxic, Integer>{

}
