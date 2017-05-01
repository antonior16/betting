package local.projects.betting.dao;

import java.util.Date;
import java.util.List;

import local.projects.betting.model.League;

public interface LeagueDao {
	/**
	 * This is the method to be used to create a record in the League table.
	 */
	public void create(League league);

	/**
	 * This is the method to be used to list down a record from the League table
	 * corresponding to a passed League id.
	 */
	public League getLeague(Integer id);

	/**
	 * This is the method to be used to list down all the records from the
	 * League table.
	 */
	public List<League> listLeagues();

	/**
	 * This is the method to be used to delete a record from the League table
	 * corresponding to a passed League id.
	 */
	public void delete(Integer id);

	/**
	 * This is the method to be used to update a record into the League table.
	 */
	public void update(League league);

	void updateLastScoreDate(Long leagueId, Date date);

	public void updateLastOddsDate(Long leagueId, Date date);

	
}