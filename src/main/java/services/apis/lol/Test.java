package services.apis.lol;



import domain.Summoner;
import services.apis.lol.Builder.LeagueInfoBuilder;
import services.apis.lol.Builder.MatchBuilder;
import services.apis.lol.Builder.SummonerBuilder;

import java.io.IOException;
import java.util.List;


public class Test {

    public static void main(String[] args) throws IOException {
        String name = "ozumas";
        String region = "euw";
        SummonerBuilder summonerBuilder = new SummonerBuilder(name,region);
        summonerBuilder.load();
        Summoner summoner = summonerBuilder.getSummoner(name);
//        LeagueInfoBuilder leagueInfoBuilder = new LeagueInfoBuilder(String.valueOf(summoner.getIdSummoner()),region);
//        leagueInfoBuilder.load();
//        leagueInfoBuilder.getSummonerLeagueInfo();
        MatchBuilder matchBuilder = new MatchBuilder(String.valueOf(summoner.getIdSummoner()),summoner.getRegion());
        matchBuilder.load();
        System.out.println(matchBuilder.getMatchs());
    }
}
