package controllers.API;

import domain.Actor;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ActorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/api")
public class AuthController extends ApiAbstractController {

    @Autowired
    private ActorService actorService;

    @ResponseBody
    @RequestMapping(value = "/isauthenticated")
    public Object auth(HttpServletRequest request, HttpServletResponse response) {
     Map<String, Object> result = new HashMap<>();

        try{
            Actor actor = actorService.findActorByPrincipal();
            result.put("roles",actor.getUserAccount().getAuthorities());
            result.put("actor",actor);
            if (actor instanceof User) {
                User user = (User) actor;
                result.put("followers", user.getFollowerUsers());
                result.put("following", user.getFollowingUsers());
            }
            result.put("authenticated",true);
        }catch (Exception e){
            result.put("authenticated",false);
        }
        return result;
    }
}
