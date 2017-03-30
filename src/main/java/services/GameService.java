package services;

import domain.Game;
import domain.Summoner;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.GameRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {


    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll(){
        return new ArrayList<>(gameRepository.findAll());
    }
    public Game getGameByTag(String tag){
    	return gameRepository.getGameByTag(tag);
    }


    public Summoner getSummonerByUser(User user){
        return gameRepository.getSummonerByUser(user);
    }
}
