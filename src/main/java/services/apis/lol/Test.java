package services.apis.lol;



import domain.Summoner;
import services.apis.lol.Builder.LeagueInfoBuilder;
import services.apis.lol.Builder.MatchBuilder;
import services.apis.lol.Builder.MatchDetailsBuilder;
import services.apis.lol.Builder.SummonerBuilder;

import java.io.IOException;
import java.util.List;


public class Test {

    public static void main(String[] args) throws IOException {
        MatchDetailsBuilder matchDetailsBuilder = new MatchDetailsBuilder("3122633913","euw");
        matchDetailsBuilder.load();

        System.out.println(matchDetailsBuilder.getMatch());
    }
}
