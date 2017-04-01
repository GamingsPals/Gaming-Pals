package services;

import domain.Confrontation;
import domain.Participes;
import domain.ReportMatch;
import domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ConfrontationRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ConfrontationService {
    //Repositories
    @Autowired
    private ConfrontationRepository confrontationRepository;

    //Services

    //Constructor
    public ConfrontationService(){super();}

    //CRUD METHODS
    public Confrontation create(){
        Confrontation result = new Confrontation();
        result.setParticipes(new ArrayList<Participes>());
        result.setReportMatches(new ArrayList<ReportMatch>());

        return result;
    }

    public Confrontation save(Confrontation confrontation) {

        Assert.notNull(confrontation);
        return confrontationRepository.save(confrontation);

    }

    public void delete(Confrontation confrontation) {

        Assert.notNull(confrontation);
        confrontationRepository.delete(confrontation);

    }

    public Confrontation findOne(int confrontationId) {

        Confrontation result=confrontationRepository.findOne(confrontationId);

        Assert.notNull(result);

        return result;

    }

    public Collection<Confrontation> findAll() {

        Collection<Confrontation> result;
        result = confrontationRepository.findAll();

        Assert.notNull(result);

        return result;

    }
}
