package controllers.API;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ReportController extends ApiAbstractController {


    @Autowired
    private ActorService actorService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "/user/{user}/report",method = RequestMethod.POST)
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

    @RequestMapping(value = "/report/user/list")
    public Object reportedUserList(HttpServletResponse response) {
        try{
           Administrator administrator = administratorService.findByPrincipal();
           Assert.notNull(administrator);
        } catch (Exception e) {
            return unauthorized(response,null);
        }
        try{
            return reportService.findAll();
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }
}
