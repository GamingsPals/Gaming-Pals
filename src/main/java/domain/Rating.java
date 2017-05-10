
package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Rating extends DomainEntity {

	// Attributes
	private int		skill;
	private int		knowledge;
	private int		attitude;
	private String	comment;


	// Constructor
	public Rating() {
		super();
	}

	// Getters and Setters

	@Range(min = 1, max = 5)
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}

	@Range(min = 1, max = 5)
	public int getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	@Range(min = 1, max = 5)
	public int getAttitude() {
		return attitude;
	}
	public void setAttitude(int attitude) {
		this.attitude = attitude;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	// Relationships
	private User ratingUser;
	private User ratedUser;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
    @JsonIgnore
	public User getRatingUser() {
		return ratingUser;
	}

	public void setRatingUser(User valuesUser) {
		this.ratingUser = valuesUser;
	}

	@Valid
	@NotNull
    @JsonIgnore
	@ManyToOne(optional = false)
	public User getRatedUser() {
		return ratedUser;
	}

	public void setRatedUser(User isValuedUser) {
		this.ratedUser = isValuedUser;
	}

}
