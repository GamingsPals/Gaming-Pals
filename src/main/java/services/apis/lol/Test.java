package services.apis.lol;



import domain.Summoner;
import services.apis.lol.Builder.LeagueInfoBuilder;
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
        LeagueInfoBuilder leagueInfoBuilder = new LeagueInfoBuilder(String.valueOf(summoner.getIdSummoner()),region);
        leagueInfoBuilder.load();
        leagueInfoBuilder.getSummonerLeagueInfo();
        System.out.print(summoner);
    }
}
