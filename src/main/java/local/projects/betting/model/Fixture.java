package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fixture implements Serializable {
	private static final long serialVersionUID = -1259418107263305560L;
	private String status;
	private String match;
	private String homeTeamName;
	private String awayTeamName;
	@JsonProperty("date")
	private Date matchDate;
	private Result result;
	private Odds odds;
	private League league;

	public Fixture() {
	}

	public Fixture(String match, String homeTeamName, String awayTeamName, Date matchDate, Result result, Odds odds,
			League league) {
		super();
		this.match = match;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.matchDate = matchDate;
		this.result = result;
		this.odds = odds;
		this.league = league;
	}

	public Fixture(Date matchDate, String homeTeamName, String awayTeamName, Odds odds, League league) {
		super();
		this.matchDate = matchDate;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.odds = odds;
		this.league = league;
	}

	public Fixture(Date matchDate, String homeTeamName, String awayTeamName, Result result, League league) {
		super();
		this.matchDate = matchDate;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.result = result;
		this.league = league;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	@JsonProperty("date")
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Odds getOdds() {
		return odds;
	}

	public void setOdds(Odds odds) {
		this.odds = odds;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
