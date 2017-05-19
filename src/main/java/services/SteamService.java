package services;


import domain.GameInfo;
import domain.SteamAccount;
import domain.User;
import forms.SteamForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    public List<Game> filteredGamesSteam(String name) throws IOException {
        Assert.notNull(name);
        name = name.toLowerCase();
        List<Game> games = getGamesSteam();
        List<Game> result = new ArrayList<>();
        if(name.length()<4) return null;
        for(Game e: games){
            String gName = e.getName().toLowerCase();
            if (gName.startsWith(name) || gName.endsWith(name) || gName.equals(name) || gName.contains(name) ||
                    gName.contains(name)){
                result.add(e);
                if(result.size()>10) break;
            }
        }
        return result;
    }

    @Cacheable("steamgames")
    public List<Game> getGamesSteam() throws IOException {
        Builder builder = new Builder();
        builder.load();

        return builder.getAllGames();
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
                if(g.getGame().getGameid().equals(gp.getGameid())){
                    is = false;
                }
            }
            if(is){
                gamesFiltered.add(gp);
            }
        }
        for(domain.Game e: gamesFiltered){
            for(Game p: games){
                if(p.getAppid().equals(Integer.parseInt(e.getGameid())) || p.getName().equals(e.getName())){
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

    public void delete(SteamAccount gameInfo) {
        Assert.notNull(gameInfo);

        steamAccountRepository.delete(gameInfo);
    }
}
