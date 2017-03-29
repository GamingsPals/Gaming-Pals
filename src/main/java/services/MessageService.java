
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


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
	
	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		Assert.notNull(message);
		this.actorService.checkIsLogged();
		return this.messageRepository.save(message);
	}
	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}


	public Message reconstruct(final MessageForm messageForm, final BindingResult binding) {
		final Message message = this.create();
		message.setReceiver(messageForm.getReceiver());
		message.setText(messageForm.getText());
		message.setTitle(messageForm.getTitle());
		this.validator.validate(message, binding);
		return message;
	}

	public void deleteMessage(final int messageId) {
		final Message message = this.findOne(messageId);
		//Assert.isTrue(message.getReceiver().getUserAccount().getUsername().equals(LoginService.getPrincipal().getUsername()) || message.getSender().getUserAccount().getUsername().equals(LoginService.getPrincipal().getUsername()));

		//		if (message.getSender().getId() == this.actorService.getLoggedActor().getId())
		//			message.setSender(null);
		//		if (message.getSender() == null && message.getReceiver() == null)
		//			this.delete(message);
		//		else
		//			message = this.save(message);
		//
		//		if (message.getReceiver() == null || message.getReceiver().getId() == this.actorService.getLoggedActor().getId())
		//			message.setReceiver(null);
		//		if (message.getReceiver() == null && message.getSender() == null)
		//			this.delete(message);
		//		else
		//			message = this.save(message);

		if (message.getReceiver() != null && message.getReceiver().getId() == this.actorService.getLoggedActor().getId())
			message.setReceiver(null);
		if (message.getSender() != null && message.getSender().getId() == this.actorService.getLoggedActor().getId())
			message.setSender(null);

		if (message.getReceiver() == null)
			if (message.getSender() == null)
				this.delete(message);

	}
}
