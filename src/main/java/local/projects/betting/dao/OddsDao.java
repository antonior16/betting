package local.projects.betting.dao;

import java.util.List;

import local.projects.betting.model.Fixture;
import local.projects.betting.model.Odds;

public interface OddsDao {
	/**
	 * This is the method to be used to create a record in the Odds table.
	 */
	public void create(Fixture fixture);

	/**
	 * This is the method to be used to list down a record from the Odds table
	 * corresponding to a passed Odds id.
	 */
	public Odds getOdds(Integer id);

	/**
	 * This is the method to be used to list down all the records from the Odds
	 * table.
	 */
	public List<Odds> listOdds();

	/**
	 * This is the method to be used to delete a record from the Odds table
	 * corresponding to a passed Odds id.
	 */
	public void delete(Integer id);

	/**
	 * This is the method to be used to update a record into the Odds table.
	 */
	public void update(Integer id, Integer age);

	public void clearMatch();
}