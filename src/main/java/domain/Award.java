package domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Award extends DomainEntity{
    //Atributtes
    private String image;
    private String description;

    //Constructor
    public Award(){super();}

    //Getters and Setters
    @URL
    @NotBlank
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}

    @NotBlank
    @SafeHtml
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}

    //Relationships
    private Tournament tournament;

    @JsonIgnore
    @ManyToOne
    public Tournament getTournament(){return tournament;}
    public void setTournament(Tournament tournament){this.tournament=tournament;}
}
