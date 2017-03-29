
package controllers.API;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController{

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("message/list");

		result.addObject("sended", this.actorService.getLoggedActor().getSended());
		result.addObject("received", this.actorService.getLoggedActor().getReceived());

		result.addObject("requestUri", "message/actor/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final MessageForm messageForm = new MessageForm();

		Assert.notNull(messageForm);

		result = this.createEditModelAndView(messageForm);
		result.addObject("messageForm", messageForm);
		final Actor actor = this.actorService.getLoggedActor();
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(actor);
		result.addObject("users", actors);
		result.addObject("requestURI", "message/actor/edit.do");
		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final String senderName) {
		ModelAndView result;
		final MessageForm messageForm = new MessageForm();

		Assert.notNull(messageForm);
		final Actor actor = this.actorService.findActorByUsername(senderName);
		result = this.replyModelAndView(messageForm);
		result.addObject("messageForm", messageForm);
		messageForm.setReceiver(actor);
		final Collection<Actor> actors = new ArrayList<Actor>();
		actors.add(actor);

		result.addObject("requestURI", "message/actor/reply.do");

		result.addObject("actor", actors);
		result.addObject("nameActor", actor.getUserAccount().getUsername());

		result.addObject("forward", false);
		result.addObject("reply", true);
		return result;
	}

	protected ModelAndView replyModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.replyModelAndView(messageForm, null);
		result.addObject("reply", true);

		result.addObject("forward", false);
		result.addObject("actor", messageForm.getReceiver());
		return result;
	}

	protected ModelAndView replyModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("message/reply");
		result.addObject("reply", true);

		result.addObject("forward", false);
		result.addObject("actor", messageForm.getReceiver());
		result.addObject("message", message);

		return result;

	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		final Actor actor = this.actorService.getLoggedActor();
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(actor);
		result.addObject("reply", false);

		result.addObject("forward", false);
		result.addObject("users", actors);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);

		final Actor actor = this.actorService.getLoggedActor();
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(actor);
		result.addObject("users", actors);
		result.addObject("message", message);

		result.addObject("forward", false);
		result.addObject("reply", false);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageForm messageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(messageForm);
		} else

			try {
				final Message message = this.messageService.reconstruct(messageForm, binding);
				Assert.isTrue(message.getReceiver().getId() != this.actorService.getLoggedActor().getId());
				this.messageService.save(message);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");

				final Actor actor = this.actorService.getLoggedActor();
				final Collection<Actor> actors = this.actorService.findAll();
				actors.remove(actor);

				result.addObject("users", actors);
			}
		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST, params = "save")
	public ModelAndView reply(@Valid final MessageForm messageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.replyModelAndView(messageForm);
		} else

			try {
				final Message message = this.messageService.reconstruct(messageForm, binding);
				Assert.isTrue(message.getReceiver().getId() != this.actorService.getLoggedActor().getId());
				this.messageService.save(message);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.replyModelAndView(messageForm, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;

		try {
			this.messageService.deleteMessage(messageId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	// Forward
	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam final int messageId) {
		ModelAndView result;
		final MessageForm messageForm = new MessageForm();

		Assert.notNull(messageForm);
//		messageForm.setMessageId(messageId);
		final Message message = this.messageService.findOne(messageId);
		messageForm.setTitle(message.getTitle());
		messageForm.setText(message.getText());
		result = this.forwardModelAndView(messageForm);

		result.addObject("messageForm", messageForm);
		final Actor actor = this.actorService.getLoggedActor();
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(actor);
		result.addObject("users", actors);

		result.addObject("requestURI", "message/actor/edit.do");
		return result;
	}

	protected ModelAndView forwardModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.forwardModelAndView(messageForm, null);

		final Actor actor = this.actorService.getLoggedActor();
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(actor);
		result.addObject("reply", false);
		result.addObject("forward", true);
		result.addObject("users", actors);
		return result;
	}

	protected ModelAndView forwardModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("message/forward");
		result.addObject("messageForm", messageForm);

		final Actor actor = this.actorService.getLoggedActor();
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(actor);
		result.addObject("reply", false);

		result.addObject("forward", true);
		result.addObject("users", actors);
		result.addObject("message", message);

		return result;

	}

}
