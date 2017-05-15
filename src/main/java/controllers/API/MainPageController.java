package controllers.API;

import domain.Actor;
import domain.Tournament;
import domain.User;
import forms.TestForm;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.GameService;
import services.TournamentService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/api")
public class    MainPageController extends ApiAbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TournamentService tournamentService;

    @ResponseBody
    @RequestMapping(value = "/main")
    public Object main(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<User> users = userService.findBestRanked();
            List<Tournament> tournaments = tournamentService.findLatest(5);
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("games", gameService.findAll());
            result.put("bestRatedUsers", users);
            result.put("lastTournaments",tournaments);
            return result;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return notFoundError(response,null);
        }
    }


}
