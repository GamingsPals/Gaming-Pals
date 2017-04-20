package services;


import domain.GameInfo;
import domain.SteamAccount;
import domain.User;
import forms.SteamForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.SteamAccountRepository;
import services.apis.steam.Builder;
import services.apis.steam.Entity.Game;
import services.apis.steam.Entity.Games;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SteamService {

    @Autowired
    private GameService gameService;

    @Autowired
    private SteamAccountRepository steamAccountRepository;

    @Autowired
    private UserService userService;

    public SteamAccount findByUser(){
        User principal = userService.findByPrincipal();
        Assert.notNull(principal);

        return steamAccountRepository.findByUser(principal);
    }

    public List<domain.Game> byId(String id) throws IOException {
        Builder builder = new Builder(id);
        User u = userService.findByPrincipal();
        Assert.notNull(u);
        List<GameInfo> gameInfos = new ArrayList<>(u.getGameInfo());
        builder.load();
        List<Game> games = builder.getGames();
        List<domain.Game> gamesBd = gameService.findAll();
        List<domain.Game> gamesFiltered = new ArrayList<>();
        List<domain.Game> games1 = new ArrayList<>();
        for(domain.Game gp: gamesBd){
            Boolean is = true;
            for(GameInfo g: gameInfos){
                if(g.getGame().getId()==gp.getId()){
                    is = false;
                }
            }
            System.out.println(is);
            if(is){
                gamesFiltered.add(gp);
            }
        }
        for(domain.Game e: gamesFiltered){
            for(Game p: games){
                if(p.getAppid().equals(e.getGameid())){
                    games1.add(e);
                }
            }
        }
       return games1;
    }

    public void addSteamAccount(SteamForm steamForm){
        Assert.notNull(steamForm.getGames());
        Assert.notNull(steamForm.getId());
        for(domain.Game e:steamForm.getGames()){
            SteamAccount steamAccount = new SteamAccount();
            steamAccount.setSteamID(steamForm.getId());
            steamAccount.setGame(e);
            steamAccount.setUsername(steamForm.getUsername());
            steamAccount.setUser(userService.findByPrincipal());
            steamAccountRepository.save(steamAccount);
        }
    }
}
