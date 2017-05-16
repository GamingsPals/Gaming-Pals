package controllers.API;

import domain.Actor;
import domain.Tournament;
import domain.User;
import forms.TestForm;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.concurrent.TimeUnit;

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
