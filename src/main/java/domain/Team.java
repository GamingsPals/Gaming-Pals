
package domain;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Access(AccessType.PROPERTY)
public class Team extends DomainEntity {

	// Attributes
	private String	name;
	private String	picture;
	private Collection<Participes> participes;
	private User leader;

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


	// Relationships
	private Collection<User>		users;
	private Collection<Tournament>	tournaments;


    @JsonIgnoreProperties(value = {"teams"})
	@ManyToMany
	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@JsonIgnore
	@ManyToMany
	public Collection<Tournament> getTournaments(){return tournaments;}
	public void setTournaments(Collection<Tournament> tournaments){this.tournaments=tournaments;}

	@JsonIgnore
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
	public Collection<Participes> getParticipes() {
		return participes;
	}

	public void setParticipes(Collection<Participes> participes) {
		this.participes = participes;
	}

	@ManyToOne
	@JsonIgnoreProperties(value = {"teams"})
	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}
}
