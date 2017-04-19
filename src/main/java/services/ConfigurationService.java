package services;

import domain.Award;
import domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AwardRepository;
import repositories.ConfigurationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ConfigurationService {
    //Repositories
    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration getConfiguration(){
        return configurationRepository.findAll().get(0);
    }



}
