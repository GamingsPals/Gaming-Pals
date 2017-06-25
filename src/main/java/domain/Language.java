package domain;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Language extends DomainEntity {

	// Attributes
	private String language;
	private String longName;

	// Constructor
	public Language(){
		super();
	}

	//Getters and Setters 
	@NotBlank
	@SafeHtml
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	// Relationships
	private Collection<User> users;

	@Valid
	@JsonIgnore
	@ManyToMany(mappedBy = "languages")
	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@SafeHtml
	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}
}
