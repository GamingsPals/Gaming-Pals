
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonInclude;

import security.UserAccount;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	//Atributes-----------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	picture;


	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@URL
	@NotBlank
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

}
