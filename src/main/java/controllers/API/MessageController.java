package controllers.API;

import domain.Actor;
import domain.Message;
import domain.notifications.MessageNotification;
import forms.MessageForms.MessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import security.Hash;
import security.UserAccount;
import services.ActorService;
import services.MessageService;
import services.notifications.MessageNotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api")
public class MessageController extends ApiAbstractController{


    @Autowired
    MessageService messageService;

    @Autowired
    ActorService actorService;

    @Autowired
    MessageNotificationService messageNotificationService;


    @ResponseBody
    @RequestMapping(value = "/messages/recent")
    public Object getRecentMessages(HttpServletRequest request, HttpServletResponse response) {
        try{
            Actor principal = actorService.findActorByPrincipal();
            Assert.notNull(principal);
        }catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            return  messageService.messagesByUsers();
        } catch (Exception e){
            return badrequest(response,null);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/messages")
    public Object messagesSenderReceiver(@RequestParam("sender") Actor sender, @RequestParam("receiver") Actor receiver,
                                         HttpServletRequest request, HttpServletResponse response) {
        try{
            Actor principal = actorService.findActorByPrincipal();
            Assert.notNull(principal);
            Assert.isTrue(principal.equals(sender) || principal.equals(receiver));
        }catch (Exception e){
            return unauthorized(response,null);
        }
        try{
            Assert.notNull(sender);
            Assert.notNull(receiver);
            return  messageService.messagesByReceiverAndSender(receiver,sender);
        } catch (Exception e){
            return badrequest(response,null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/message/new", method = RequestMethod.POST)
    public Object example(MessageForm message, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        Actor sender;
        Actor receiver;
        try{
            sender = actorService.findActorByPrincipal();
            receiver = actorService.findActorByUsername(message.getReceiver());
            Assert.isTrue(sender.getId()!=receiver.getId());
            Assert.notNull(sender);
            Assert.notNull(receiver);
        } catch (Exception e){
            return badrequest(response,null);
        }
        try{
            messageService.createFromForm(message,sender,receiver);
        }catch (Exception e){
            return internalservererror(response,e.getMessage());

        }

        return ok(response,null);
    }

}
