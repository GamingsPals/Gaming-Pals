package services;

import domain.Tournament;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TournamentServiceTest extends AbstractTest {

    @Autowired
    private TournamentService tournamentService;

    @Test
    public void samplePositiveTest() {
        authenticate("admin");
        Tournament tournament = tournamentService.findOne(26);
        Assert.notNull(tournament);
        tournamentService.delete(tournament);
        Assert.isNull(tournamentService.findOne(26));

    }
}
