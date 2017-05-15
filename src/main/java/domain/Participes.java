
package domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Participes extends DomainEntity {

	//Attributes
	private boolean isWinner;


	//Constructor
	public Participes() {
		super();
	}

	//Getters and Setters
	public boolean getIsWinner() {
		return isWinner;
	}
	public void setIsWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}


	//Relationships
	private Team team;
	private Collection<Confrontation> confrontations;


	@ManyToOne
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}

	@JsonIgnore
	@ManyToMany(mappedBy = "participes",cascade = CascadeType.ALL)
    public Collection<Confrontation> getConfrontations() {
        return confrontations;
    }

    public void setConfrontations(Collection<Confrontation> confrontations) {
        this.confrontations = confrontations;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

}
