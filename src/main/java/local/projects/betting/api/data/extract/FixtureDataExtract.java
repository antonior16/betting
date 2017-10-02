package local.projects.betting.api.data.extract;

import java.util.List;
import java.util.Map;

import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;

public interface FixtureDataExtract extends DataExtract {
	public List<Fixture> extractFixtures(League league, String timeFrame);
}
