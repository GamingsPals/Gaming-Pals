
package domain;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
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
	private String password;
	private Collection<ReportMatch> reportMatches;

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
	@ManyToMany(mappedBy = "teams")
	public Collection<Tournament> getTournaments(){return tournaments;}
	public void setTournaments(Collection<Tournament> tournaments){this.tournaments=tournaments;}

	@JsonIgnore
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL,orphanRemoval = true)
	public Collection<Participes> getParticipes() {
		return participes;
	}

	public void setParticipes(Collection<Participes> participes) {
		this.participes = participes;
	}

	@ManyToOne
    @JsonIgnore
	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	@JsonIgnore
    @Length(min = 6, max = 28)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Transients

	private Integer idLeader;

	@Transient
	public Integer getIdLeader() {
        if(getLeader()==null) return 0;
	    return getLeader().getId();
	}

	public void setIdLeader(Integer idLeader) {
		this.idLeader = idLeader;
	}


	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
	public Collection<ReportMatch> getReportMatches() {
		return reportMatches;
	}

	public void setReportMatches(Collection<ReportMatch> reportMatches) {
		this.reportMatches = reportMatches;
	}
}
