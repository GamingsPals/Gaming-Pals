package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring/config/packages.xml"
})
@Transactional
public class LoLApiServiceTest {


    @Autowired
    private LoLApiService loLApiService;


    @Test
    public void testGetSummoner() throws IOException {
        loLApiService.getSummonerByName("ozumas", "euw");
    }
}
