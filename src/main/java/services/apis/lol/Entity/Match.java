package services.apis.lol.Entity;

import java.util.Date;
import java.util.List;

public class Match {

	private long id;
	private Champion championId;
	private boolean isWin;
	private List<Integer> summonerIdTeam;
	private Date createdGame;
	private Integer kills,assists,deaths;
	private List<Item> itemsGame;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Champion getChampionId() {
		return championId;
	}
	public Date getCreatedGame() {
		return createdGame;
	}
	public void setCreatedGame(Date createdGame) {
		this.createdGame = createdGame;
	}
	public void setChampionId(Champion championId) {
		this.championId = championId;
	}
	public boolean getIsWin() {
		return isWin;
	}
	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
	}
	public List<Integer> getSummonerIdTeam() {
		return summonerIdTeam;
	}
	public void setSummonerIdTeam(List<Integer> summonerIdTeam) {
		this.summonerIdTeam = summonerIdTeam;
	}
	public Integer getKills() {
		return kills;
	}
	public void setKills(Integer kills) {
		this.kills = kills;
	}
	public Integer getAssists() {
		return assists;
	}
	public void setAssists(Integer assists) {
		this.assists = assists;
	}
	public Integer getDeaths() {
		return deaths;
	}
	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}
	public List<Item> getItemsGame() {
		return itemsGame;
	}
	public void setItemsGame(List<Item> itemsGame) {
		this.itemsGame = itemsGame;
	}
	@Override
	public String toString() {
		return "Match [id=" + id + ", championId=" + championId + ", isWin=" + isWin + ", summonerIdTeam="
				+ summonerIdTeam + "]";
	}
	
	
}
