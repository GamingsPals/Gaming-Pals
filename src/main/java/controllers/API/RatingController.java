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

@RestController
@RequestMapping("/api")
public class RatingController extends ApiAbstractController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;


    @RequestMapping(value = "/user/{user}/rate",method = RequestMethod.POST)
    public Object rate(@PathVariable User user, Rating rating, HttpServletResponse response)
            throws Exception{
        System.out.println( rating.getAttitude());
        try{
            Assert.notNull(user);
        } catch (Exception e) {
            return notFoundError(response,null);
        }
        try{
            Assert.notNull(actorService.findActorByPrincipal());
        } catch (Exception e) {
            return unauthorized(response,null);
        }
        try{
            Assert.isTrue(user.getId() != actorService.findActorByPrincipal().getId());
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            ratingService.rateUser(rating, user);
            return ok(response,null);
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }
}
