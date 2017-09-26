package local.projects.betting.dao;

import local.projects.betting.model.Fixture;

public interface FixtureDao {
	/**
	 * This is the method to be used to create a record in the Odds table.
	 */
	public void save(Fixture fixture);
}