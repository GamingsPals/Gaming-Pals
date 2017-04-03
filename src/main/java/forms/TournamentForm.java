
package forms;

import java.util.Date;

public class TournamentForm {

	private String	title;
	private String	description;
	private String	rules;
	private Integer	numberTeams;
	private Date	limitInscription;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public Integer getNumberTeams() {
		return numberTeams;
	}

	public void setNumberTeams(Integer numberTeams) {
		this.numberTeams = numberTeams;
	}

	public Date getLimitInscription() {
		return limitInscription;
	}

	public void setLimitInscription(Date limitInscription) {
		this.limitInscription = limitInscription;
	}

}
