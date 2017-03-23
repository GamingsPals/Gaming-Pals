package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
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
		this.ratingAvg = 0.;
		this.skillAvg = 0.;
		this.knowledgeAvg = 0.;
		this.attitudeAvg = 0.;

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
	private Collection<Language>		languages;
    private Collection<GameInfo> gameInfo;
    private Double ratingAvg;
    private Double attitudeAvg;
    private Double knowledgeAvg;
    private Double skillAvg;
    private Collection<Report> reportsDone;
    private Collection<Report> reportsReceived;



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


	
	@NotEmpty
	@Valid
	@ManyToMany()
	public Collection<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(Collection<Language> languages) {
		this.languages = languages;
	}



    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
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


	public Double getRatingAvg() {
		return ratingAvg;
	}

	public void setRatingAvg(Double ratingAvg) {
		this.ratingAvg = ratingAvg;
	}



    @PrePersist
    protected void onCreateRating() {
	    this.ratingAvg = 0.;
    }

    @PreUpdate
    protected void onUpdate() {
	    Double avgRating = 0.;
	    Double avgSkill = 0.;
	    Double avgKnowledge = 0.;
	    Double avgAttitude = 0.;
	    for(Rating e : this.getRatingsReceived()){
	        avgSkill += e.getSkill();
	        avgKnowledge += e.getKnowledge();
	        avgAttitude += e.getAttitude();
        }

        this.ratingAvg = (avgSkill + avgKnowledge + avgAttitude) / 3;
	    this.attitudeAvg = avgAttitude;
	    this.skillAvg = avgSkill;
	    this.knowledgeAvg = avgKnowledge;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "reporterUser")
    public Collection<Report> getReportsDone() {
        return reportsDone;
    }

    public void setReportsDone(Collection<Report> reportsDone) {
        this.reportsDone = reportsDone;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "reportedUser")
    public Collection<Report> getReportsReceived() {
        return reportsReceived;
    }

    public void setReportsReceived(Collection<Report> reportsReceived) {
        this.reportsReceived = reportsReceived;
    }

	public Double getAttitudeAvg() {
		return attitudeAvg;
	}

	public void setAttitudeAvg(Double attitudeAvg) {
		this.attitudeAvg = attitudeAvg;
	}

	public Double getKnowledgeAvg() {
		return knowledgeAvg;
	}

	public void setKnowledgeAvg(Double knowledgeAvg) {
		this.knowledgeAvg = knowledgeAvg;
	}

	public Double getSkillAvg() {
		return skillAvg;
	}

	public void setSkillAvg(Double skillAvg) {
		this.skillAvg = skillAvg;
	}
}
