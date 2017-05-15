package services;


import domain.GameInfo;
import domain.SteamAccount;
import domain.Summoner;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.GameInfoRepository;

import javax.transaction.Transactional;
import java.util.Collection;


@Transactional
@Service
public class GameInfoService {

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LoLApiService loLApiService;

    @Autowired
    private SteamService steamService;

    public GameInfo save(GameInfo gameInfo){
        User u = userService.findByPrincipal();
        Assert.notNull(u);
        Assert.notNull(gameInfo);

        return gameInfoRepository.save(gameInfo);
    }

    public  void delete(GameInfo gameInfo){
        User u = userService.findByPrincipal();
        Assert.notNull(u);
        Assert.notNull(gameInfo);
        Assert.isTrue(u.getGameInfo().contains(gameInfo));
        Collection<GameInfo> gameInfos = u.getGameInfo();
        gameInfos.remove(gameInfo);
        u.setGameInfo(gameInfos);

        userService.save(u);


    }
}
