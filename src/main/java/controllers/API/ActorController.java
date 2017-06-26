package controllers.API;

import domain.Actor;
import domain.User;
import forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ActorService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping(value = "/actor/{actor2}/available")
    public Object available(@RequestParam("mode") String mode, @PathVariable String actor2, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try{
            switch (mode){
                case "user":{
                    Actor actor = actorService.findByUserAccountUsername(actor2);
                    if(actor==null){
                        result.put("available",true);
                    }else{
                        result.put("available",false);
                    }
                    break;
                }
                case "email":{
                    Actor actor = actorService.findByEmail(actor2);
                    if(actor==null){
                        result.put("available",true);
                    }else{
                        result.put("available",false);
                    }
                    break;
                }
                default:{
                    result.put("available",false);
                }
            }

        }catch (Exception e){
            result.put("available",false);
        }

        return result;
    }


}
