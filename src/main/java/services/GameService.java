package services;

import domain.Game;
import domain.Summoner;
import domain.User;
import forms.SteamGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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

    public Game findOne(Integer integer) {

        return gameRepository.findOne(integer);
    }

    public void addGame(SteamGame steamGame) {
        Assert.notNull(steamGame);
        Game game = new Game();
        game.setGameid(steamGame.getGameid());
        game.setHeader(steamGame.getHeader());
        game.setName(steamGame.getName());
        game.setTag(steamGame.getTag());
        game.setPicture(steamGame.getPicture());

        save(game);
    }

    private Game save(Game game) {
        Assert.notNull(game);

        return gameRepository.save(game);
    }
}
