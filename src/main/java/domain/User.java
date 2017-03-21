
package domain;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import serializers.CustomRatingsSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Attributes
	private int					age;
	private boolean				verify;
	private CreditCard			creditCard;


	// Constructor
	public User() {
		super();
	}

	//Getters and Setters 
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}



	@NotNull
	@Valid
    @OneToOne
    @JsonIgnore
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	// Relationships
	private Collection<Team>			teams;
	private Collection<Rating> 			ratingsDone;
	private Collection<Rating> 			ratingsReceived;
	private Collection<User>			followingUsers;
	private Collection<User>			followerUsers;
	private Collection<ReportUser>		createReports;
	private Collection<Language>		languages;
    private Collection<GameInfo> 		gameInfo;


	@Valid
	@ManyToMany(mappedBy = "users")
	@JsonIgnore
	public Collection<Team> getTeams() {
		return teams;
	}

	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}

	@Valid
	@OneToMany(mappedBy = "ratingUser")
	public Collection<Rating> getRatingsDone() {
		return ratingsDone;
	}

	public void setRatingsDone(Collection<Rating> valuesRatings) {
		this.ratingsDone = valuesRatings;
	}

	@Valid
	@OneToMany(mappedBy = "ratedUser")
	public Collection<Rating> getRatingsReceived() {
		return ratingsReceived;
	}

	public void setRatingsReceived(Collection<Rating> isValuedRatings) {
		this.ratingsReceived = isValuedRatings;
	}

	@Valid
	@ManyToMany
    @JsonIgnore
	public Collection<User> getFollowingUsers() {
		return followingUsers;
	}

	public void setFollowingUsers(Collection<User> followingUsers) {
		this.followingUsers = followingUsers;
	}

	@Valid
	@ManyToMany(mappedBy = "followingUsers")
	@JsonIgnore
	public Collection<User> getFollowerUsers() {
		return followerUsers;
	}

	public void setFollowerUsers(Collection<User> followerUsers) {
		this.followerUsers = followerUsers;
	}

	@Valid
	@OneToMany(mappedBy = "userReporter")
	@JsonIgnore
	public Collection<ReportUser> getCreateReports() {
		return createReports;
	}

	public void setCreateReports(Collection<ReportUser> createReports) {
		this.createReports = createReports;
	}
	
	@NotEmpty
	@Valid
	@ManyToMany()
	public Collection<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(Collection<Language> languages) {
		this.languages = languages;
	}



    @OneToMany(mappedBy = "user")
        public Collection<GameInfo> getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(Collection<GameInfo> gameUsers) {
        this.gameInfo = gameUsers;
    }

	public boolean isVerify() {
		return verify;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}


}
