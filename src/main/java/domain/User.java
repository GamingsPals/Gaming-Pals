
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import domain.notifications.TeamInvitationNotification;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Access(AccessType.PROPERTY)

public class User extends Actor {

	// Attributes
	private Integer		age;
	private boolean	verify;
	private Date	lastpaid;
	private String	header;


	// Constructor
	public User() {
		super();
		this.ratingAvg = 0.;
		this.skillAvg = 0.;
		this.knowledgeAvg = 0.;
		this.attitudeAvg = 0.;

	}

	//Getters and Setters
	@NotNull
	public Integer getAge() {
		return this.age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Date getLastpaid() {
		return this.lastpaid;
	}

	public void setLastpaid(final Date lastpaid) {
		this.lastpaid = lastpaid;
	}


	// Relationships
	private Collection<Team>						teams;
	private Collection<Rating>						ratingsDone;
	private Collection<Rating>						ratingsReceived;
	private Collection<User>						followingUsers;
	private Collection<User>						followerUsers;
	private Collection<Language>					languages;
	private Collection<GameInfo>					gameInfo;
	private Double									ratingAvg;
	private Double									attitudeAvg;
	private Double									knowledgeAvg;
	private Double									skillAvg;
	private Collection<Report>						reportsDone;
	private Collection<Report>						reportsReceived;
	private Collection<TeamInvitationNotification>	teamInvitationNotifications;
	private Collection<Team>                        teamsLeaded;


	@Valid
	@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
	@JsonIgnore
	public Collection<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(final Collection<Team> teams) {
		this.teams = teams;
	}

	@Valid
	@OneToMany(mappedBy = "ratingUser")
	public Collection<Rating> getRatingsDone() {
		return this.ratingsDone;
	}

	public void setRatingsDone(final Collection<Rating> valuesRatings) {
		this.ratingsDone = valuesRatings;
	}

	@Valid
	@OneToMany(mappedBy = "ratedUser")
	public Collection<Rating> getRatingsReceived() {
		return this.ratingsReceived;
	}

	public void setRatingsReceived(final Collection<Rating> isValuedRatings) {
		this.ratingsReceived = isValuedRatings;
	}

	@Valid
	@ManyToMany
	@JsonIgnore
	public Collection<User> getFollowingUsers() {
		return this.followingUsers;
	}

	public void setFollowingUsers(final Collection<User> followingUsers) {
		this.followingUsers = followingUsers;
	}

	@Valid
	@ManyToMany(mappedBy = "followingUsers")
	@JsonIgnore
	public Collection<User> getFollowerUsers() {
		return this.followerUsers;
	}

	public void setFollowerUsers(final Collection<User> followerUsers) {
		this.followerUsers = followerUsers;
	}

	@NotEmpty
	@Valid
	@ManyToMany()
	public Collection<Language> getLanguages() {
		return this.languages;
	}

	public void setLanguages(final Collection<Language> languages) {
		this.languages = languages;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	public Collection<GameInfo> getGameInfo() {
		return this.gameInfo;
	}

	public void setGameInfo(final Collection<GameInfo> gameUsers) {
		this.gameInfo = gameUsers;
	}

	public boolean isVerify() {
		return this.verify;
	}

	public void setVerify(final boolean verify) {
		this.verify = verify;
	}

	public Double getRatingAvg() {
		return this.ratingAvg;
	}

	public void setRatingAvg(final Double ratingAvg) {
		this.ratingAvg = ratingAvg;
	}

	@PrePersist
	protected void onCreateRating() {
		this.ratingAvg = 0.;
	}

	@PreUpdate
	public void onUpdate() {
		if(getRatingsReceived().size()!=0){
		Double avgSkill = 0.;
		Double avgKnowledge = 0.;
		Double avgAttitude = 0.;
		for (final Rating e : this.getRatingsReceived()) {
			avgSkill += e.getSkill();
			avgKnowledge += e.getKnowledge();
			avgAttitude += e.getAttitude();
		}
		avgSkill =avgSkill / getRatingsReceived().size();
		avgKnowledge = avgKnowledge / getRatingsReceived().size();
		avgAttitude=avgAttitude / getRatingsReceived().size();
		this.ratingAvg = (avgSkill + avgKnowledge + avgAttitude) / 3;
		this.attitudeAvg = avgAttitude;
		this.skillAvg = avgSkill;
		this.knowledgeAvg = avgKnowledge;
		}
	}

	@JsonIgnore
	@OneToMany(mappedBy = "reporterUser")
	public Collection<Report> getReportsDone() {
		return this.reportsDone;
	}

	public void setReportsDone(final Collection<Report> reportsDone) {
		this.reportsDone = reportsDone;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "reportedUser")
	public Collection<Report> getReportsReceived() {
		return this.reportsReceived;
	}

	public void setReportsReceived(final Collection<Report> reportsReceived) {
		this.reportsReceived = reportsReceived;
	}

	public Double getAttitudeAvg() {
		return this.attitudeAvg;
	}

	public void setAttitudeAvg(final Double attitudeAvg) {
		this.attitudeAvg = attitudeAvg;
	}

	public Double getKnowledgeAvg() {
		return this.knowledgeAvg;
	}

	public void setKnowledgeAvg(final Double knowledgeAvg) {
		this.knowledgeAvg = knowledgeAvg;
	}

	public Double getSkillAvg() {
		return this.skillAvg;
	}

	public void setSkillAvg(final Double skillAvg) {
		this.skillAvg = skillAvg;
	}

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	public Collection<TeamInvitationNotification> getTeamInvitationNotifications() {
		return this.teamInvitationNotifications;
	}

	public void setTeamInvitationNotifications(final Collection<TeamInvitationNotification> teamInvitationNotifications) {
		this.teamInvitationNotifications = teamInvitationNotifications;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(final String header) {
		this.header = header;
	}

	@OneToMany(mappedBy = "leader")
    @JsonIgnore
    public Collection<Team> getTeamsLeaded() {
        return teamsLeaded;
    }

    public void setTeamsLeaded(Collection<Team> teamsLeaded) {
        this.teamsLeaded = teamsLeaded;
    }
}
