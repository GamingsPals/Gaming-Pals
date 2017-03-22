package controllers.API;

import domain.Rating;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import services.ActorService;
import services.RatingService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@RequestMapping("/api")
public class UserController extends ApiAbstractController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @ResponseBody
    @RequestMapping(value = "/user/{user2}")
    public Object usersBestRanked(@PathVariable String user2, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = userService.findByUserAccountUsername(user2);
            Assert.notNull(user2);
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("actor",user);
            result.put("followers", user.getFollowerUsers());
            result.put("following",user.getFollowingUsers());
            result.put("ratings", user.getRatingsReceived());
            result.put("teams",user.getTeams());
            return  result;
        } catch (Exception e){
            return notFoundError(response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/{user}/follow")
    public Object followOrUnfollow(@PathVariable User user,HttpServletRequest request, HttpServletResponse response){
       try{
           Assert.notNull(user);
       } catch (Exception e) {
           return notFoundError(response);
       }
        try{
            Assert.notNull(actorService.findActorByPrincipal());
        } catch (Exception e) {
            return unauthorized(response);
        }
        try{
           Assert.isTrue(user.getId() != actorService.findActorByPrincipal().getId());
        } catch (Exception e){
           return badrequest(response);
        }
        try{
            userService.followOrUnfollowUser(user);
            return ok(response);
        } catch (Exception e){
            return internalservererror(response);
        }
    }

}
