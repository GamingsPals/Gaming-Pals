package services.apis.lol.Entity;

import domain.Summoner;

import java.util.Date;
import java.util.List;

public class Match {

	private long id;
	private Integer championId;
	private boolean isWin;
	private List<MatchSummoner> summonerTeam;
	private Date createdGame;
	private Integer kills,assists,deaths;
	private List<Integer> itemsGame;
	private List<MatchSummoner> enemyTeam;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedGame() {
		return createdGame;
	}
	public void setCreatedGame(Date createdGame) {
		this.createdGame = createdGame;
	}

	public boolean getIsWin() {
		return isWin;
	}
	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
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

    public List<Integer> getItemsGame() {
        return itemsGame;
    }

    public void setItemsGame(List<Integer> itemsGame) {
        this.itemsGame = itemsGame;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }


    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public List<MatchSummoner> getSummonerTeam() {
        return summonerTeam;
    }

    public void setSummonerTeam(List<MatchSummoner> summonerTeam) {
        this.summonerTeam = summonerTeam;
    }

    public List<MatchSummoner> getEnemyTeam() {
        return enemyTeam;
    }

    public void setEnemyTeam(List<MatchSummoner> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }
}
