package controllers.API;

import domain.Game;
import domain.SteamAccount;
import domain.User;
import forms.SteamForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import services.SteamService;
import services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/api")
public class SteamController extends ApiAbstractController{

    @Autowired
    UserService userService;

    @Autowired
    SteamService steamService;

    @ResponseBody
    @RequestMapping("/steam/{id}")
    public Object getGamesBySteamID(@PathVariable String id, HttpServletResponse response){
        Assert.notNull(id);
        try {
            User u = userService.findByPrincipal();
        } catch (Exception e){
            return unauthorized(response,null);
        }
        try {
            SteamAccount steamAccount = steamService.findByUser();
            if(!(steamAccount==null || steamAccount.getSteamID().equals(id))){
                throw new Exception();
            }
            Collection<Game> games = steamService.byId(id);
            return games;
        } catch (Exception e) {
            return internalservererror(response,e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/steam/add", method = RequestMethod.POST)
    public Object addGames(SteamForm steamForm, HttpServletResponse response){
        try{
            Assert.notNull(steamForm);
            Assert.notNull(steamForm.getId());
            Assert.notNull(steamForm.getGames());
        } catch (Exception e){
            return badrequest(response,null);
        }
        try {
            User u = userService.findByPrincipal();
        } catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            steamService.addSteamAccount(steamForm);

            return ok(response,null);
        }catch (Exception e){
            return internalservererror(response,e.getMessage());
        }
    }
}
