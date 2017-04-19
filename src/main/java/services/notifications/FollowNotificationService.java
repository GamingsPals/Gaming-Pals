package services.notifications;

import domain.Actor;
import domain.notifications.FollowNotification;
import domain.notifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.notifications.FollowNotificationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class FollowNotificationService {
    //Repositories
    @Autowired
    private FollowNotificationRepository followNotificationRepository;


    public FollowNotification save(FollowNotification team) {

        Assert.notNull(team);
        return followNotificationRepository.save(team);

    }

    public void delete(FollowNotification team) {

        Assert.notNull(team);
        followNotificationRepository.delete(team);

    }

    public FollowNotification findOne(int teamId) {
        System.out.print(teamId);
        FollowNotification result= followNotificationRepository.findOne(teamId);
        System.out.println(result);
        Assert.notNull(result);

        return result;

    }

    public Collection<FollowNotification> findAll() {

        Collection<FollowNotification> result;
        result = followNotificationRepository.findAll();

        Assert.notNull(result);

        return result;

    }

    public Boolean isNotification(Actor follower, Actor following){
        return followNotificationRepository.isNotification(follower, following) != null;
    }

    public Collection<FollowNotification> findByActorNews(Actor actor) {
        return followNotificationRepository.findActorByNews(actor);
    }
}
