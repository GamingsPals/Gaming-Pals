package controllers.API;

import com.google.gson.JsonElement;
import domain.Summoner;
import domain.User;
import forms.LoLApiTestForm;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Error;
import services.GameService;
import services.LoLApiService;
import services.UserService;
import services.apis.lol.Entity.Mastery;
import services.apis.lol.Entity.Match;
import utilities.HashPassword;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/lol")
public class LoLApiController extends ApiAbstractController{
	@Autowired
	private GameService gameService;
    @Autowired
    private LoLApiService loLApiService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/addsummoner/{name}/{region}")
    public Object action1post(@PathVariable String name,@PathVariable String region, @RequestParam("key") String key,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {
        Summoner test;
        User user;

        try{
            test = loLApiService.getSummonerByName(name, region);
            Assert.notNull(test);
        } catch (Exception e){
            return notFoundError(response,"Summoner doesn't exist!");
        }
        try{
            Assert.isTrue(key.equals(HashPassword.md5(name).substring(0,25)));
            Assert.isTrue(loLApiService.checkMasteryVerified(test,key));
            user = userService.findByPrincipal();
            Assert.notNull(user);
        }catch (Exception e){
            return unauthorized(response, "Key not verified");
        }

        test.setGame(gameService.getGameByTag("lol"));
        test.setUser(user);

        try{
            loLApiService.saveSummoner(test);
            return ok(response,null);
        }catch (Exception e){
            return internalservererror(response,"There was something wrong with your request!");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/stats/{user}")
    public Object action1list(@PathVariable User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Summoner summoner;
        try{
           summoner = gameService.getSummonerByUser(user);
        } catch (Exception e){
            return notFoundError(response,"Summoner not found");
        }
        try{
            Map<String,Object> result = new HashMap<>();
            List<Match> matchsBySummonerId = loLApiService
                    .getMatchsBySummonerId(String.valueOf(summoner.getIdSummoner()), summoner.getRegion());
            result.put("matches",matchsBySummonerId);
            result.put("summoner",summoner);

            return result;
        } catch (Exception e){
            return internalservererror(response,"Interal server error");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/stats/match/{id}")
    public Object match(@PathVariable String id, @RequestParam("region") String region, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            return loLApiService.getMatch(id,region);
        } catch (Exception e){
            return notFoundError(response,"Match not found");
        }
    }


}
