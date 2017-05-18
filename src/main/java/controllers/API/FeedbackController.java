package controllers.API;

import domain.Actor;
import domain.feedback.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import services.ActorService;
import services.feedback.FeedbackService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class FeedbackController extends ApiAbstractController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ActorService actorService;

    @RequestMapping("/feedback/all")
    public Object all(HttpServletResponse response){
        Actor actor;
        try{
            actor =actorService.findActorByPrincipal();
            Assert.notNull(actor);
        }catch (Exception e){
            return unauthorized(response);
        }
        try{
            return feedbackService.findAllParents();
        } catch (Exception e){
            return internalservererror(response);
        }
    }

    @RequestMapping(value = "/feedback/add",method = RequestMethod.POST)
    public Object add(@RequestParam("body") String body,
                      @RequestParam(value = "parent", required = false) Feedback parent, HttpServletResponse response){
        Actor actor;
        try{
            actor =actorService.findActorByPrincipal();
            Assert.notNull(actor);
        }catch (Exception e){
            return unauthorized(response);
        }
        try{
            Assert.notNull(body);
        } catch (Exception e){
            return badrequest(response);
        }
        try{
            String result = feedbackService.add(body,parent);
            if(result!=null){
                return badrequest(response,result);
            }

            return ok(response);
        } catch (Exception e){
            return internalservererror(response,e.getMessage());
        }
    }

    @RequestMapping(value = "/feedback/{feedback}/{like}")
    public Object like(@PathVariable("feedback") Feedback feedback, @PathVariable("like") Boolean like, HttpServletResponse response){
        Actor actor;
        try{
            actor =actorService.findActorByPrincipal();
            Assert.notNull(actor);
        }catch (Exception e){
            return unauthorized(response);
        }
        try{
            Assert.notNull(feedback);
            Assert.notNull(like);
        } catch (Exception e){
            return badrequest(response);
        }
        try{
            feedbackService.likeOrDislike(feedback,like);
            return all(response);
        } catch (Exception e){
            return internalservererror(response,e.getMessage());
        }
    }
}
