package services;

import domain.League;
import domain.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.LeagueRepository;
import repositories.SummonerRepository;
import services.apis.lol.Builder.ChampionBuilder;
import services.apis.lol.Builder.LeagueInfoBuilder;
import services.apis.lol.Builder.MatchBuilder;
import services.apis.lol.Builder.SummonerBuilder;
import services.apis.lol.Entity.Champion;
import services.apis.lol.Entity.Match;

import java.io.IOException;
import java.util.List;

@Service
public class LoLApiService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    public LoLApiService(){
        super();
    }


    public domain.Summoner findByName(String name, String region){
            return summonerRepository.findOneByName(name,region);
    }

    private void saveSummoner(Summoner summoner){
            if (summoner.getLeagues()!= null){
                for(League l: summoner.getLeagues()){
                    l.setSummoner(summoner);
                    leagueRepository.save(l);
                }
            }
    }

    public Summoner getSummonerByName(String name, String region) throws IOException {
        Summoner persistedSummoner = findByName(name,region);
        if (persistedSummoner!=null) return persistedSummoner;
        name = name.toLowerCase();
        SummonerBuilder summonerBuilder = new SummonerBuilder(name,region);
        summonerBuilder.load();
       Summoner summoner = summonerBuilder.getSummoner(name);
        if (summoner==null) return null;
        LeagueInfoBuilder leagueInfoBuilder = new LeagueInfoBuilder(String.valueOf(summoner.getIdSummoner()),region);
        leagueInfoBuilder.load();
        summoner.setLeagues(leagueInfoBuilder.getSummonerLeagueInfo());
        System.out.println(summoner);
        saveSummoner(summoner);

        return  summoner;
    }
    public List<Match> getMatchsBySummonerId(String sumonnerId, String region) throws IOException{
    	MatchBuilder matchBuilder= new MatchBuilder(sumonnerId,region);
    	matchBuilder.load();
		return matchBuilder.getMatchs();
    }
    public Champion getChampionById(String id,String region) throws IOException{
    	ChampionBuilder championBuilder=new ChampionBuilder(id,region);
    	championBuilder.load();
    	return championBuilder.getChampion();
    }
}
