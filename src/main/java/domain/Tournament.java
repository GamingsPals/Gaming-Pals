
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

import org.codehaus.jackson.annotate.JsonIgnore;
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
	private String	rules;
	private Integer	numberTeams;
	private Date	limitInscription;


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
	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	@Range(min = 2, max = 32)
	public Integer getNumberTeams() {
		return numberTeams;
	}

	public void setNumberTeams(Integer numberTeams) {
		this.numberTeams= numberTeams;
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


	//Relationships
	private Collection<Match> matchs;
	private Collection<Award> awards;


	@Valid
	@JsonIgnore
	@OneToMany(mappedBy = "tournament")
	public Collection<Match> getMatchs() {
		return matchs;
	}
	public void setMatchs(Collection<Match> matchs) {
		this.matchs= matchs;
	}

	@Valid
	@JsonIgnore
	@OneToMany(mappedBy = "tournament")
	public Collection<Award> getAwards(){return awards;}
	public void setAwards(Collection<Award> awards){this.awards=awards;}


}
