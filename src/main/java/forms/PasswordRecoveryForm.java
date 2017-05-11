package forms;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordRecoveryForm {
	
	private String username;
	private String	email;
	
	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
