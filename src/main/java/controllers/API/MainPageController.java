package controllers.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.GameService;
import services.tournaments.TournamentService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public Object main(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
            return userService.mainStatData();
    }


}
