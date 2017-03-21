
package domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class ReportUser extends Report {

	//Constructor
	public ReportUser() {
		super();
	}


	private User userReporter;
	private User userReported;
	

	@Valid
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	public User getUserReporter() {
		return userReporter;
	}

	public void setUserReporter(User userReporter) {
		this.userReporter = userReporter;
	}
	
	@Valid
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	public User getUserReported() {
		return userReported;
	}

	public void setUserReported(User userReported) {
		this.userReported = userReported;
	}

}
