
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
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
	private String picture;
	private Game game;
	private Integer players;

	@Transient
	public Integer getMaxplayers() {
		return players+2;
	}

	@Range(min = 1,max=10)
	public Integer getPlayers() {
		return players;
	}

	public void setPlayers(Integer players) {
		this.players = players;
	}

	@ManyToOne
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	// Constructor
	public Tournament() {
		super();
	}

	// Getters and Setters
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	@NotNull
	public Date getMomentCreate() {
		return momentCreate;
	}
	public void setMomentCreate(Date momentCreate) {
		this.momentCreate = momentCreate;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	@SafeHtml
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
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	@NotNull
	public Date getLimitInscription() {
		return limitInscription;
	}

	public void setLimitInscription(Date limitInscription) {
		this.limitInscription = limitInscription;
	}

	@SafeHtml
	public String getPicture(){return picture;}
	public void setPicture(String picture){this.picture=picture;}


	//Relationships
	private Collection<Confrontation> confrontations;
	private Collection<Award> awards;
	private Collection<Team> teams;


	@Valid
	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL,orphanRemoval=true)
	public Collection<Confrontation> getConfrontations() {
		return confrontations;
	}
	public void setConfrontations(Collection<Confrontation> confrontations) {
		this.confrontations = confrontations;
	}

	@Valid
	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL,orphanRemoval=true)
	public Collection<Award> getAwards(){return awards;}
	public void setAwards(Collection<Award> awards){this.awards=awards;}

	@ManyToMany
	public Collection<Team> getTeams(){return teams;}
	public void setTeams(Collection<Team> teams){this.teams=teams;}


}
