package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.apis.steam.Entity.Game;
import services.tournaments.TournamentService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.io.IOException;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TournamentServiceTest extends AbstractTest {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SteamService steamService;

    @Test
    public void samplePositiveTest() {
        authenticate("Faker");
        tournamentService.tournamentsAvailablePrincipal(tournamentService.findOne(31));

    }
    @Test
    public void sample2PositiveTest() throws IOException {
        for(Game g: steamService.getGamesSteam()){
            System.out.println(g.getName());
        }

    }
}
