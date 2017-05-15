
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Confrontation extends DomainEntity {

	//Attributes
	private int		round;
	private int		numberMatch;
	private Date	limitPlay;


	//Constructor
	public Confrontation() {
		super();
	}

	//// Getters and Setters
	@Min(1)
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}

	@Min(1)
	public int getNumberMatch() {
		return numberMatch;
	}
	public void setNumberMatch(int numberMatch) {
		this.numberMatch = numberMatch;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	@NotNull
	public Date getLimitPlay() {
		return limitPlay;
	}
	public void setLimitPlay(Date limitPlay) {
		this.limitPlay = limitPlay;
	}


	//Relationships
	private Collection<Participes>	participes;
	private Collection<ReportMatch>	reportMatches;
	private Tournament				tournament;


	@Valid
	@ManyToMany(cascade = CascadeType.ALL)
	public Collection<Participes> getParticipes() {
		return participes;
	}
	public void setParticipes(Collection<Participes> participes) {
		this.participes = participes;
	}

	@Valid
	@OneToMany(mappedBy = "confrontation",cascade = CascadeType.REMOVE)
	public Collection<ReportMatch> getReportMatches() {
		return reportMatches;
	}
	public void setReportMatches(Collection<ReportMatch> reportMatches) {
		this.reportMatches = reportMatches;
	}

	@JsonIgnore
	@ManyToOne
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

}
