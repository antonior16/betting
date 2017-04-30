package local.projects.betting.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import local.projects.betting.model.League;
import local.projects.betting.model.Odds;

public interface FixturesDao {
	Map<League, Date> getFixturesWithoutResults();
}
