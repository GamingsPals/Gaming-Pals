
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tournament extends DomainEntity {

	// Attributes
	private Date	momentCreate;
	private String	title;
	private String	description;
	private String	prize;
	private Integer	limitTeams;
	private Date	limitInscription;
	private Date	limitEnd;


	// Constructor
	public Tournament() {
		super();
	}

	// Getters and Setters
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMomentCreate() {
		return momentCreate;
	}
	public void setMomentCreate(Date momentCreate) {
		this.momentCreate = momentCreate;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	@Range(min = 4, max = 50)
	public Integer getLimitTeams() {
		return limitTeams;
	}

	public void setLimitTeams(Integer limitTeams) {
		this.limitTeams = limitTeams;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getLimitInscription() {
		return limitInscription;
	}

	public void setLimitInscription(Date limitInscription) {
		this.limitInscription = limitInscription;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(Date limitEnd) {
		this.limitEnd = limitEnd;

	}


	//Relationships
	private Collection<Team> teams;


	@Valid
	@ManyToMany(mappedBy = "tournaments")
	public Collection<Team> getTeams() {
		return teams;
	}

	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}


}
