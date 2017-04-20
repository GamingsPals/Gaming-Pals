package controllers.API;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import services.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class SteamController extends ApiAbstractController{

    @Autowired
    UserService userService;

    @RequestMapping("/steam/{id}")
    public Object getGamesBySteamID(@PathVariable String id, HttpServletResponse response){
        Assert.notNull(id);
        try {
            User u = userService.findByPrincipal();
        } catch (Exception e){
            return unauthorized(response,null);
        }
        return new Object();

    }
}
