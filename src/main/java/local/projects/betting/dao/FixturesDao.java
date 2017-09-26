package local.projects.betting.dao;

import java.util.Date;
import java.util.Map;

import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;

public interface FixturesDao {
	void save(Fixture fixture);
}
