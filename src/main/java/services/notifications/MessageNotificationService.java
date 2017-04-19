package services.notifications;

import domain.Actor;
import domain.notifications.MessageNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.notifications.MessageNotificationRepository;

import javax.transaction.Transactional;
import java.text.CollationElementIterator;
import java.util.Collection;

@Service
@Transactional
public class MessageNotificationService {
    //Repositories
    @Autowired
    private MessageNotificationRepository messageNotificationRepository;


    public MessageNotification save(MessageNotification team) {

        Assert.notNull(team);
        return messageNotificationRepository.save(team);

    }

    public void delete(MessageNotification team) {

        Assert.notNull(team);
        messageNotificationRepository.delete(team);

    }

    public MessageNotification findOne(int teamId) {
        System.out.print(teamId);
        MessageNotification result= messageNotificationRepository.findOne(teamId);
        System.out.println(result);
        Assert.notNull(result);

        return result;

    }

    public Collection<MessageNotification> findAll() {

        Collection<MessageNotification> result;
        result = messageNotificationRepository.findAll();

        Assert.notNull(result);

        return result;

    }

    public MessageNotification isNotification(Actor receiver, Actor sender) {
        return messageNotificationRepository.isNotification(sender,receiver);
    }

    public Collection<MessageNotificationService> findByActorNews(Actor actor) {
        return messageNotificationRepository.findAllByActorNews(actor);
    }
}
