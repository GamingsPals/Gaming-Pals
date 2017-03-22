package services;

import domain.League;
import domain.Summoner;
import domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.LeagueRepository;
import repositories.SummonerRepository;
import services.apis.lol.Builder.ChampionBuilder;
import services.apis.lol.Builder.LeagueInfoBuilder;
import services.apis.lol.Builder.MasteryBuilder;
import services.apis.lol.Builder.MatchBuilder;
import services.apis.lol.Builder.SummonerBuilder;
import services.apis.lol.Entity.Champion;
import services.apis.lol.Entity.Mastery;
import services.apis.lol.Entity.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoLApiService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private SummonerRepository summonerRepository;
    @Autowired
    private UserService userService;

    public LoLApiService(){
        super();
    }


    public domain.Summoner findByName(String name, String region){
            return summonerRepository.findOneByName(name,region);
    }
    public domain.Summoner findByIdSummoner(int id, String region){
    	System.out.println(id+region);
        return summonerRepository.findOneById(id,region);
}
    public void saveSummoner(Summoner summoner){
            if (summoner.getLeagues()!= null){
                for(League l: summoner.getLeagues()){
                    l.setSummoner(summoner);
                    leagueRepository.save(l);
                }
            }
            User connected=userService.findByPrincipal();
        	summoner.setUser(connected);
        	summonerRepository.save(summoner);
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
    public Summoner getSummonerById(int id, String region) throws IOException {
        Summoner persistedSummoner = findByIdSummoner(id, region);
        if (persistedSummoner!=null) return persistedSummoner;
        SummonerBuilder summonerBuilder = new SummonerBuilder(id,region);
        summonerBuilder.load();
       Summoner summoner = summonerBuilder.getSummoner(id+"");
        if (summoner==null) return null;
        LeagueInfoBuilder leagueInfoBuilder = new LeagueInfoBuilder(String.valueOf(summoner.getIdSummoner()),region);
        leagueInfoBuilder.load();
        List<League> leagues=new ArrayList<League>();
        if(leagueInfoBuilder.getSummonerLeagueInfo()!=null){
        	leagues=leagueInfoBuilder.getSummonerLeagueInfo();
        }
        summoner.setLeagues(leagues);
        System.out.println(summoner);

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
    public List<Mastery> getMasteryBySummonerId(String sumonnerId, String region) throws IOException{
    	MasteryBuilder masteryBuilder= new MasteryBuilder(sumonnerId,region);
    	masteryBuilder.load();
		return masteryBuilder.getMasteries();
    }
}
