
package domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.feedback.Feedback;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonInclude;

import security.UserAccount;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	//Atributes-----------------------------------------------------------------

	private String	email;
	private String	picture;

	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@SafeHtml
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


	//Relationships----------------------------------------------------------------
	private UserAccount			userAccount;
	private Collection<Message>	sended;
	private Collection<Message>	received;
	private Collection<Feedback> feedbacks;

	@JsonIgnore
	@OneToMany(mappedBy = "author")
	public Collection<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Collection<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@Valid
	@JsonIgnore
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getSended() {
		return this.sended;
	}

	public void setSended(final Collection<Message> sended) {
		this.sended = sended;
	}

	@NotNull
	@Valid
	@JsonIgnore
	@OneToMany(mappedBy = "receiver")
	public Collection<Message> getReceived() {
		return this.received;
	}

	public void setReceived(final Collection<Message> received) {
		this.received = received;
	}


	private Map<String,Object> lastMessageSended;

	@Transient
	public Map<String, Object> getLastMessageSended() {
		return lastMessageSended;
	}

	public void setLastMessageSended(Map<String, Object> lastMessageSended) {
		this.lastMessageSended = lastMessageSended;
	}
}
