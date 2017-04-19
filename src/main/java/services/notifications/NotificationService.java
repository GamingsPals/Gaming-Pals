package services.notifications;

import domain.Actor;
import domain.notifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.notifications.NotificationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class NotificationService {
    //Repositories
    @Autowired
    private NotificationRepository notificationRepository;


    public Notification save(Notification team) {

        Assert.notNull(team);
        return notificationRepository.save(team);

    }

    public void delete(Notification team) {

        Assert.notNull(team);
        notificationRepository.delete(team);

    }

    public Notification findOne(int teamId) {
        System.out.print(teamId);
        Notification result= notificationRepository.findOne(teamId);
        System.out.println(result);
        Assert.notNull(result);

        return result;

    }

    public Collection<Notification> findAll() {

        Collection<Notification> result;
        result = notificationRepository.findAll();

        Assert.notNull(result);

        return result;

    }

    public Collection<Notification> findByActorNews(Actor actor) {
        return notificationRepository.findByActorNews(actor);
    }
}
