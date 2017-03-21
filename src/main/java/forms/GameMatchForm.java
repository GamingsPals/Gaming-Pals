package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class GameMatchForm {
	
	// Attributes
	private Date moment;
	private String result;

	// Constructor
	public GameMatchForm() {
		super();
	}

	// Getters and Setters

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
