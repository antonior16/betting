package local.projects.betting.model.json;

import java.util.List;

import local.projects.betting.model.Team;

public class TeamsCollection {
	private List<Team> teams;

	public TeamsCollection() {
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

}
