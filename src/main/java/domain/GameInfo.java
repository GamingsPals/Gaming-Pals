
package domain;




import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;


@Entity
@Access(AccessType.PROPERTY)
public abstract class GameInfo extends DomainEntity {

	private User		user;
	protected String	username;
	private Game		game;


	@ManyToOne
    @JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@SafeHtml
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@ManyToOne
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
