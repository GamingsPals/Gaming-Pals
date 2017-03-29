
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date				moment;
	private String				title;
	private String				text;
	private boolean 			toxic;

	
	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public boolean getToxic() {
		return this.toxic;
	}
	public void setToxic(final boolean toxic) {
		this.toxic = toxic;
	}


	//Relationships
	private Actor	sender;
	private Actor	receiver;


	@Valid
	@JsonIgnore
	@ManyToOne(optional = true)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@JsonIgnore
	@ManyToOne(optional = true)
	public Actor getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

}
