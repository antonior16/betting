package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

public class League implements Serializable {
	private static final long serialVersionUID = -2308174004403590109L;

	private Long leagueId;
	private String name;
	private String oddsUrl;
	private String scoresUrl;
	private Date lastOddsUpdate;
	private Date lastResultsUpdate;

	public League() {
	}

	public League(Long leagueId, String name, String oddsUrl, String scoresUrl, Date lastOddsUpdate,
			Date lastResultsUpdate) {
		this.leagueId = leagueId;
		this.name = name;
		this.oddsUrl = oddsUrl;
		this.scoresUrl = scoresUrl;
		this.lastOddsUpdate = lastOddsUpdate;
		this.lastResultsUpdate = lastResultsUpdate;
	}

	public Long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOddsUrl() {
		return oddsUrl;
	}

	public void setOddsUrl(String oddsUrl) {
		this.oddsUrl = oddsUrl;
	}

	public String getScoresUrl() {
		return scoresUrl;
	}

	public void setScoresUrl(String scoresUrl) {
		this.scoresUrl = scoresUrl;
	}

	public Date getLastOddsUpdate() {
		return lastOddsUpdate;
	}

	public void setLastOddsUpdate(Date lastOddsUpdate) {
		this.lastOddsUpdate = lastOddsUpdate;
	}

	public Date getLastResultsUpdate() {
		return lastResultsUpdate;
	}

	public void setLastResultsUpdate(Date lastResultsUpdate) {
		this.lastResultsUpdate = lastResultsUpdate;
	}

	@Override
	public String toString() {
		return "League [name=" + name + ", booksUrl=" + oddsUrl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oddsUrl == null) ? 0 : oddsUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		League other = (League) obj;
		if (oddsUrl == null) {
			if (other.oddsUrl != null)
				return false;
		} else if (!oddsUrl.equals(other.oddsUrl))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
