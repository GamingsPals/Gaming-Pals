
package domain;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Access(AccessType.PROPERTY)
public class GameInfo extends DomainEntity {

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

	@Override
	public String toString() {
		return "GameInfo{" +
				"user=" + user +
				", username='" + username + '\'' +
				", game=" + game +
				'}';
	}
}
