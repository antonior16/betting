package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

public class Fixture implements Serializable {
	private static final long serialVersionUID = -1259418107263305560L;
	private String match;
	private Team homeTeamName;
	private Team awayTeamName;
	private Date matchDate;
	private Result result;
	private Odds odds;
	private League league;

	public Fixture() {
	}

	public Fixture(String match, Team homeTeamName, Team awayTeamName, Date matchDate, Result result, Odds odds,
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

	public Fixture(Date matchDate, Team homeTeamName, Team awayTeamName, Odds odds, League league) {
		super();
		this.matchDate = matchDate;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.odds = odds;
		this.league = league;
	}

	public Fixture(Date matchDate, Team homeTeamName, Team awayTeamName, Result result, League league) {
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

	public Team getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(Team homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public Team getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(Team awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public Date getMatchDate() {
		return matchDate;
	}

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

}
