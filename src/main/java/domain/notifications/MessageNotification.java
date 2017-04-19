package domain.notifications;


import domain.Message;
import domain.Actor;

import javax.persistence.Access;

import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class MessageNotification extends Notification {

    private Actor sender;
    private Actor receiver;
    private int numMessages;

    public MessageNotification() {
        this.type = Type.Message;
        this.numMessages = 0;
    }

    @OneToOne
    public Actor getSender() {
        return sender;
    }

    public void setSender(Actor sender) {
        this.sender = sender;
    }

    @OneToOne
    public Actor getReceiver() {
        return receiver;
    }

    public void setReceiver(Actor receiver) {
        this.receiver = receiver;
    }


    public int getNumMessages() {
        return numMessages;
    }

    public void setNumMessages(int numMessages) {
        this.numMessages = numMessages;
    }
}
