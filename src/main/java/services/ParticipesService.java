package services;

import domain.Participes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ParticipesRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ParticipesService {
    //Repositories
    @Autowired
    private ParticipesRepository participesRepository;

    //Services

    //Constructor
    public ParticipesService(){super();}

    //CRUD METHODS
    public Participes create(){
        Participes result = new Participes();
        result.setIsWinner(false);

        return result;
    }

    public Participes save(Participes participes) {

        Assert.notNull(participes);
        return participesRepository.save(participes);

    }

    public void delete(Participes participes) {

        Assert.notNull(participes);
        participesRepository.delete(participes);

    }

    public Participes findOne(int participesId) {

        Participes result=participesRepository.findOne(participesId);

        Assert.notNull(result);

        return result;

    }

    public Collection<Participes> findAll() {

        Collection<Participes> result;
        result = participesRepository.findAll();

        Assert.notNull(result);

        return result;

    }
}
