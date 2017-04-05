package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

public class ReportMatchForm {
    //Constructor
    public ReportMatchForm(){super();}

    //Attributes
    private String image;
    private String result;
    private String description;

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
}
