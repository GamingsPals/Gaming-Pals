package controllers.API;

import domain.*;
import forms.ReportMatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.ReportMatchService;
import services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ReportMatchController extends ApiAbstractController{
    //Services
    @Autowired
    private UserService userService;

    @Autowired
    private ReportMatchService reportMatchService;

    @RequestMapping(value = "/user/{confrontation}/reportMatch",method = RequestMethod.POST)
    public Object reportConfrontation(@PathVariable Confrontation confrontation, ReportMatchForm reportMatchForm, HttpServletResponse response)
            throws Exception{
        Team t1 = new Team();
        Team t2 = new Team();
        User user = userService.findByPrincipal();
        try{
            Assert.notNull(confrontation);
        } catch (Exception e) {
            return notFoundError(response,null);
        }
        try{
            Assert.notNull(reportMatchForm);
        } catch (Exception e) {
            return notFoundError(response,null);
        }
        try{
            int acum = 0;
            for(Participes p:confrontation.getParticipes()){
                if(acum==0){
                    t1 = p.getTeam();
                }else{
                    t2 = p.getTeam();
                }
                acum++;
            }
            Assert.isTrue(t1.getUsers().contains(user)
                    || t2.getUsers().contains(user));
        } catch (Exception e) {
            return unauthorized(response,null);
        }try{
            for(ReportMatch rp:confrontation.getReportMatches()){
                Assert.isTrue(!user.getTeams().contains(rp.getTeam()));
            }
        }catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            ReportMatch reportMatch = reportMatchService.reconstruct(reportMatchForm);
            if(t1.getUsers().contains(user)){
                reportMatchService.reportConfrontation(reportMatch, t1, confrontation);
                return ok(response, null);
            }else {
                reportMatchService.reportConfrontation(reportMatch, t2, confrontation);
                return ok(response, null);
            }
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }
}
