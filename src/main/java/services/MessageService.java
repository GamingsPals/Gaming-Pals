
package services;

import java.util.*;

import domain.Actor;
import domain.notifications.MessageNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import domain.Message;
import services.notifications.MessageNotificationService;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	MessageNotificationService messageNotificationService;


	public MessageService() {
		super();
	}

	public Message create() {
		this.actorService.checkIsLogged();
		final Message res = new Message();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setSender(this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername()));
		return res;
	}

	public List<Message> messagesByReceiverAndSender(Actor receiver, Actor sender){
		Assert.notNull(sender);
		Assert.notNull(receiver);
		List<Message> aux = new ArrayList<>(sender.getSended());
		aux.addAll(sender.getReceived());
		List<Message> result = new ArrayList<>();
		for(Message e: aux){
		    if ((e.getReceiver()== receiver && e.getSender() == sender) ||
                    (e.getSender() == receiver && e.getReceiver() == sender)){
		        result.add(e);
            }
        }
		return result;
	}
	
	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		Assert.notNull(message);

		return this.messageRepository.save(message);
	}
	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

	public Collection<Actor> messagesByUsers(){
		Actor actor = actorService.findActorByPrincipal();
        Assert.notNull(actor);
        Set<Actor> actors = new HashSet<>();
        for(Message m: actor.getSended()){
            actors.add(m.getReceiver());
		}
		for(Message m:actor.getReceived()){
            actors.add(m.getSender());
		}
		actors.remove(actor);
        for(Actor a: actors){
		    List<Message> messages = new ArrayList<>(a.getSended());
		    messages.addAll(a.getReceived());
            List<Message> messagesSendedAndReceivedByActor = new ArrayList<>();
		    for(Message m:messages){
		        if(m.getSender().equals(actor) || m.getReceiver().equals(actor)){
		            messagesSendedAndReceivedByActor.add(m);
                }
            }
            messagesSendedAndReceivedByActor.sort((o1, o2) -> {
                if (o1.getMoment().getTime() == o2.getMoment().getTime()) return 0;
                    return (o1.getMoment().after(o2.getMoment())) ? -1 : 1;
            });
            Map<String,Object> map = new HashMap<>();
            Message lastMessageSended = messagesSendedAndReceivedByActor.get(0);
            map.put("message",lastMessageSended.getText());
            map.put("moment",lastMessageSended.getMoment());
            map.put("reply",!lastMessageSended.getSender().equals(actor));
            a.setLastMessageSended(map);
        }

        List<Actor> result = new ArrayList<>(actors);
        result.sort((o1, o2)->{
            Date o1Date = (Date) o1.getLastMessageSended().get("moment");
            Date o2Date = (Date) o2.getLastMessageSended().get("moment");
           if(o1Date.getTime() == o2Date.getTime()) return 0;
           return (o1Date.after(o2Date)) ? 1 : -1;
        });
        return result;
	}


    public void createFromForm(forms.MessageForms.MessageForm message, Actor sender, Actor receiver) {
		Message newMessage = new Message();
		newMessage.setReceiver(receiver);
		newMessage.setSender(sender);
		newMessage.setText(message.getMessage());
		newMessage = save(newMessage);
		createNotification(message,sender,receiver,newMessage);
    }

    private void createNotification(forms.MessageForms.MessageForm message, Actor sender, Actor receiver, Message newMessage){
		if(message.isNotification()){
		    MessageNotification persistedMnotification = messageNotificationService.isNotification(receiver,sender);
			MessageNotification notification = (persistedMnotification !=null) ? persistedMnotification : new MessageNotification();
			notification.setReceiver(receiver);
			notification.setSender(sender);
			notification.setActor(receiver);
			notification.setNumMessages(notification.getNumMessages()+1);
			MessageNotification messageNotification = messageNotificationService.save(notification);
		}
	}
}
