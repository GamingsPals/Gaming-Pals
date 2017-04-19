package controllers.API;

import domain.Actor;
import domain.User;
import forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ActorService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Controller
@RequestMapping("/api")
public class ActorController extends ApiAbstractController {

    @Autowired
    ActorService actorService;

    @ResponseBody
    @RequestMapping(value = "/actor/all")
    public Object search(HttpServletRequest request, HttpServletResponse response) {
        try {
            Actor actor = actorService.findActorByPrincipal();
            Assert.notNull(actor);
            return actorService.findAll();
        }catch (Exception e){
            return unauthorized(response,null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/actor/{actor2}")
    public Object findOne(@PathVariable String actor2, HttpServletRequest request, HttpServletResponse response) {
        try {
            Assert.notNull(actorService.findActorByPrincipal());
        }catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            Actor actor = actorService.findByUserAccountUsername(actor2);
            Assert.notNull(actor);

            return actor;
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }
}
