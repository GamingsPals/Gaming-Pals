package controllers.API;

import domain.Actor;
import domain.GameInfo;
import domain.User;
import forms.PasswordRecoveryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import security.UserAccount;
import services.ActorService;
import services.GameInfoService;
import services.UserService;
import services.login.MailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameInfoController extends ApiAbstractController {

    
    @Autowired
    private UserService userService;

    @Autowired
    private GameInfoService gameInfoService;

    @RequestMapping("/gameinfo/{gameInfo}/delete")
    public Object remove(@PathVariable("gameInfo") GameInfo gameInfo,HttpServletResponse response){
        try{
            Assert.notNull(gameInfo);
        } catch (Exception e){
            return notFoundError(response,null);
        }
        try{
             User user = userService.findByPrincipal();
             Assert.notNull(user);
             Assert.isTrue(user.getGameInfo().contains(gameInfo));
        } catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            gameInfoService.delete(gameInfo);

            return ok(response,null);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return internalservererror(response,null);
        }
    }
}
