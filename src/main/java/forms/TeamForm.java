package forms;

import domain.User;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.Collection;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamForm {

	private String name;
	private String picture;
	private Collection<User> members;
	private String password;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Collection<User> getMembers() {
		return members;
	}

	public void setMembers(Collection<User> members) {
		this.members = members;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
