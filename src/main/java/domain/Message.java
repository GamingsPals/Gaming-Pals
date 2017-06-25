
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date				moment;
	private String				text;


	public Message(){
	    this.moment = new Date();
    }

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	//Relationships
	private Actor	sender;
	private Actor	receiver;


	@Valid
	@JsonIgnoreProperties({"userAccount", "sended", "received", "followerUsers",
			"followingUsers", "ratingsReceived", "ratingsDone", "teams", "reportsDone", "reportsReceived" })
	@ManyToOne
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
    @JsonIgnoreProperties({"userAccount", "sended", "received", "followerUsers",
            "followingUsers", "ratingsReceived", "ratingsDone", "teams", "reportsDone", "reportsReceived" })
	@ManyToOne(optional = true)
	public Actor getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

}
