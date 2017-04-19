package controllers.API;

import domain.*;
import domain.notifications.TeamInvitationNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.ActorService;
import services.notifications.TeamInvitationNotificationService;
import services.TeamService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamInvitationController extends ApiAbstractController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamInvitationNotificationService teamInvitationNotificationService;


    @RequestMapping(value = "/invitations/{invitation}/accept")
    public Object accept(@PathVariable("invitation") TeamInvitationNotification invitation, HttpServletResponse response)
            throws Exception{
        Actor actor = actorService.findActorByPrincipal();
        User user;
        try{
            Assert.notNull(invitation);
        }catch (Exception e){
            return notFoundError(response,null);
        }
        try{
            user = (User) actor;
            Assert.isTrue(invitation.getUser().getId() == user.getId());
        } catch (Exception e){
            return unauthorized(response,null);
        }
        try {
            Team team = invitation.getTeam();
            List<User> userList = new ArrayList<>(team.getUsers());
            Assert.isTrue(!userList.contains(user));
            userList.add(user);
            team.setUsers(userList);
            teamService.save(team);
            invitation.setReaded(true);
            teamInvitationNotificationService.save(invitation);
            return ok(response,null);
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }

    @RequestMapping(value = "/invitations/{invitation}/reject")
    public Object reject(TeamInvitationNotification invitation, HttpServletResponse response)
            throws Exception{
        Actor actor = actorService.findActorByPrincipal();
        User user;
        try{
            Assert.notNull(invitation);
        }catch (Exception e){
            return notFoundError(response,null);
        }
        try{
            user = (User) actor;
            Assert.isTrue(invitation.getUser().getId() == user.getId());
        } catch (Exception e){
            return unauthorized(response,null);

        }
        try {
            invitation.setReaded(true);
            teamInvitationNotificationService.save(invitation);
            return ok(response,null);
        } catch (Exception e){
            return internalservererror(response,null);
        }
    }
}
