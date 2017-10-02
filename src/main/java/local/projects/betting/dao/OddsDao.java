package local.projects.betting.dao;

import java.util.List;

import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;

public interface OddsDao {
	/**
	 * This is the method to be used to create a record in the Odds table.
	 */
	public void save(Fixture fixture);

	public void clearMatch();

	/**
	 *
	 */
	public List<League> getLeagueByOddsMatchDay();
}