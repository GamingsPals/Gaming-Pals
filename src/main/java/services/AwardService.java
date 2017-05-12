package services;

import domain.Award;
import forms.AwardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AwardRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AwardService {
    //Repositories
    @Autowired
    private AwardRepository awardRepository;

    //Services

    //Constructor
    public AwardService(){super();}

    //CRUD METHODS
    public Award create(){
        Award result = new Award();
        return result;
    }
    public Award save(Award award) {

        Assert.notNull(award);
        return awardRepository.save(award);

    }

    public void delete(Award award) {

        Assert.notNull(award);
        awardRepository.delete(award);

    }

    public Award findOne(int awardId) {

        Award result=awardRepository.findOne(awardId);

        Assert.notNull(result);

        return result;

    }

    public Collection<Award> findAll() {

        Collection<Award> result;
        result = awardRepository.findAll();

        Assert.notNull(result);

        return result;

    }

    //Other bussiness method
    public Award reconstruct(AwardForm awardForm){
        Award award = create();
        award.setDescription(awardForm.getDescription());
        award.setImage(awardForm.getImage());
        return award;
    }

}
