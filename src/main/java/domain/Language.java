package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Language extends DomainEntity {

	// Attributes
	private String language;
	
	// Constructor
	public Language(){
		super();
	}

	//Getters and Setters 
	@NotBlank
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
	
}
