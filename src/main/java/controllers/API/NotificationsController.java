package controllers.API;

import domain.Actor;
import domain.notifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.ActorService;
import services.notifications.FollowNotificationService;
import services.notifications.MessageNotificationService;
import services.notifications.NotificationService;
import services.notifications.TeamInvitationNotificationService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NotificationsController  extends ApiAbstractController{


    @Autowired
    private ActorService actorService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FollowNotificationService followNotificationService;

    @Autowired
    private MessageNotificationService messageNotificationService;

    @Autowired
    private TeamInvitationNotificationService teamInvitationNotificationService;


    @RequestMapping(value = "/notifications/news")
    public Object all(HttpServletResponse response) throws Exception {
        Actor actor;
        Map<String, Object> result = new HashMap<>();
        try {
            actor = actorService.findActorByPrincipal();
            Assert.notNull(actor);
        } catch (Exception e) {
            return unauthorized(response, null);
        }
        try {
            result.put("Follower", followNotificationService.findByActorNews(actor));
            result.put("Message", messageNotificationService.findByActorNews(actor));
            result.put("TeamInvitations", teamInvitationNotificationService.findByActorNews(actor));

            return result;
        } catch (Exception e) {
            return internalservererror(response, null);
        }
    }

    @RequestMapping(value = "/notifications/{notification}/read")
    public Object cred(@PathVariable Notification notification, HttpServletResponse response) throws Exception {
        Actor actor;
        try {
            actor = actorService.findActorByPrincipal();
            Assert.notNull(actor);
            Assert.isTrue(actor.getId() == notification.getActor().getId());
        } catch (Exception e) {
            return unauthorized(response, null);
        }
        try {
            notification.setReaded(true);
            notificationService.save(notification);
            return ok(response,null);
        } catch (Exception e) {
            return internalservererror(response, null);
        }
    }
}
