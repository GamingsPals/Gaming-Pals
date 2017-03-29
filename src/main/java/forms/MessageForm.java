
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.Actor;

public class MessageForm {

	private String				title;
	private String				text;
	private Actor				receiver;
//	private int					messageId;
//
//
//	public int getMessageId() {
//		return this.messageId;
//	}
//
//	public void setMessageId(final int messageId) {
//		this.messageId = messageId;
//	}

	public Actor getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
}
