/**
 *
 */
package local.projects.betting.model.json;

import java.util.List;

import local.projects.betting.model.League;

public class LeaguesCollection {
	private List<League> leaguesList;

	public LeaguesCollection() {
	}

	public List<League> getLeaguesList() {
		return leaguesList;
	}

	public void setLeaguesList(List<League> leaguesList) {
		this.leaguesList = leaguesList;
	}
}
