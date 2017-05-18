package domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
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
    private Boolean revised;



    //Constructor
    public ReportMatch(){
        super();
        revised = false;
    }

    public Boolean getRevised() {
        return revised;
    }

    public void setRevised(Boolean revised) {
        this.revised = revised;
    }

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
    private Confrontation confrontation;
    private Team team;

    @Valid
    @ManyToOne
    @NotNull
    public Confrontation getConfrontation(){return confrontation;}
    public void setConfrontation(Confrontation confrontation){this.confrontation= confrontation;}


    @ManyToOne
    public Team getTeam(){return team;}
    public void setTeam(Team team){this.team=team;}

}
