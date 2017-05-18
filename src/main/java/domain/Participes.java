
package domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Participes extends DomainEntity {

	//Attributes
	private boolean winner;
	private boolean played;


	//Constructor
	public Participes() {
		super();
		winner = false;
		played = false;
	}

	//Relationships
	private Team team;
	private Confrontation confrontation;


	@ManyToOne
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}

	@JsonIgnore
	@ManyToOne
    public Confrontation getConfrontation() {
        return confrontation;
    }

    public void setConfrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
    }

    public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}
}
