
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Team extends DomainEntity {

	// Attributes
	private String	name;
	private String	picture;


	// Constructor
	public Team() {
		super();
	}

	// Getters and Setters
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


	// Relationships
	private Collection<User>		users;
	private Collection<Tournament>	tournaments;


	@NotEmpty
	@Valid
	@JsonIgnore
	@ManyToMany()
	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Valid
	@JsonIgnore
	@ManyToMany
	public Collection<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Collection<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

}
