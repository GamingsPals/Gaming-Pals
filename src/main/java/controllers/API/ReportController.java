package controllers.API;

import domain.Rating;
import domain.Report;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.ActorService;
import services.RatingService;
import services.ReportService;
import services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ReportController extends ApiAbstractController {


    @Autowired
    private ActorService actorService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "/user/{user}/report",method = RequestMethod.POST)
    public Object rate(@PathVariable User user, Report report, HttpServletResponse response)
            throws Exception{
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
            reportService.report(user,report);
            return ok(response);
        } catch (Exception e){
            return internalservererror(response);
        }
    }
}
