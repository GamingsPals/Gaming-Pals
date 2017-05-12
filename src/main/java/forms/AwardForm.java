package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class AwardForm {
    //Atributtes
    private String image;
    private String description;

    //Constructor
    public AwardForm(){super();}

    //Getters and Setters
    @URL
    @NotBlank
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}

    @NotBlank
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}
}