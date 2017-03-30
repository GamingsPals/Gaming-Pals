package domain;



import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Participes extends DomainEntity{

    //Attributes
    private boolean isWinner;

    //Constructor
    public Participes(){super();}

    //Getters and Setters
    public boolean getIsWinner(){return isWinner;}
    public void setIsWinner(boolean isWinner){this.isWinner=isWinner;}

    //Relationships
    private Team team;

    @Valid
    @JsonIgnore
    @ManyToOne
    public Team getTeam(){return team;}
    public void setTeam(Team team){this.team=team;}
}
