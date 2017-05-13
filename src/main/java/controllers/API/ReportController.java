package controllers.API;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import services.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ReportController extends ApiAbstractController {


    @Autowired
    private ActorService actorService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "/user/report/{user}",method = RequestMethod.POST)
    public Object rate(@PathVariable User user, Report report, HttpServletResponse response)
            throws Exception{
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
            reportService.report(user,report);
            return ok(response,null);
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }

    @RequestMapping(value = "/admin/report/{report}/delete")
    public Object deleteReport(@PathVariable Report report, HttpServletResponse response){
        try{
            administratorService.checkIsAdmin();
        } catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            Assert.notNull(report);
        } catch (Exception e){
            return notFoundError(response,null);
        }
        try{
            reportService.delete(report);

            return ok(response,null);
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }


}
