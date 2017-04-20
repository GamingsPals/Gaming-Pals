package services;


import domain.GameInfo;
import domain.SteamAccount;
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

    public List<domain.Game> byId(String id) throws IOException {
        Builder builder = new Builder("76561197960434622");
        builder.load();
        List<Game> games = builder.getGames();
        List<domain.Game> gamesBd = gameService.findAll();
        List<domain.Game> games1 = new ArrayList<>();
        for(domain.Game e: gamesBd){
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
            steamAccountRepository.save(steamAccount);
        }
    }
}
