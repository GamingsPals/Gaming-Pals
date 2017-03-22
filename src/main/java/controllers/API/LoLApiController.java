package controllers.API;

import domain.Summoner;
import forms.LoLApiTestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.GameService;
import services.LoLApiService;
import services.apis.lol.Entity.Mastery;
import services.apis.lol.Entity.Match;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/lol")
public class LoLApiController extends ApiAbstractController{
	@Autowired
	private GameService gameService;
    @Autowired
    private LoLApiService loLApiService;

    @ResponseBody
    @RequestMapping(value = "/user/{name}/{region}")
    public Object action1post(@PathVariable String name,@PathVariable String region, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Summoner test = loLApiService.getSummonerByName(name, region);
        return test;
    }
    @ResponseBody
    @RequestMapping(value = "/matchs")
    public Object action1list(@RequestParam Integer summonerId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Match> test = loLApiService.getMatchsBySummonerId(summonerId.toString(), "euw");
        System.out.print(test);
        return test;
    }
    @ResponseBody
    @RequestMapping(value = "/vinculated/{summonerId}/{region}")
    public Map<String,Object> action1vinculated(@PathVariable Integer summonerId,@PathVariable String region) throws IOException {
    	Map<String,Object> result= new LinkedHashMap<>();
    	Random r=new Random();
        String aux="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String test="";
        Summoner summoner=loLApiService.getSummonerById(summonerId, region);
        for(int i=0;i<=20;i++){
        test+=aux.charAt(r.nextInt(aux.length()));
        }
        result.put("summoner", summoner);
        result.put("keyLol", test);
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/vinculatedSave/{summonerId}/{region}")
    public Object action1vinculatedSave(@PathVariable Integer summonerId,@PathVariable String region) throws IOException {
    	Summoner test = loLApiService.getSummonerById(summonerId, "euw");
    	test.setGame(gameService.getGameByTag("lol"));
		loLApiService.saveSummoner(test);
        return test;
    }
    @ResponseBody
    @RequestMapping(value = "/user/mastery/{summonerId}/{region}")
    public Object action1vinculatedpost(@PathVariable Integer summonerId,@PathVariable String region) throws IOException {
        List<Mastery> masteries=loLApiService.getMasteryBySummonerId(summonerId.toString(), region);
        return masteries;
}



}
