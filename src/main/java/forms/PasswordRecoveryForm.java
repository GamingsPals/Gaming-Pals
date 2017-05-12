package forms;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordRecoveryForm {

	private String	email;

	
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
