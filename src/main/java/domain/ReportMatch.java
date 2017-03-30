package domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class ReportMatch extends DomainEntity{
    //Attributes
    private String image;
    private String result;
    private String description;

    //Constructor
    public ReportMatch(){super();}

    //Getters and Setters
    @NotBlank
    @URL
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}

    @Pattern(regexp = "^Winner$|^Losser$|^Incidence$")
    public String getResult(){return result;}
    public void setResult(String result){this.result=result;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}

    //Relationship
    private Match match;
    private Team team;

    @Valid
    @JsonIgnore
    @ManyToOne
    @NotNull
    public Match getMatch(){return match;}
    public void setMatch(Match match){this.match=match;}

    @Valid
    @JsonIgnore
    @ManyToOne
    @NotNull
    public Team getTeam(){return team;}
    public void setTeam(Team team){this.team=team;}

}
