package forms;

import javax.validation.constraints.NotNull;

public class SearchForm {
	
	// Attributes
	private String game;
	private String tier;
	
	// Constructor
	public SearchForm() {
		super();
	}

	// Getters and Setters
	@NotNull
	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}
	
	@NotNull
	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}
	
}
