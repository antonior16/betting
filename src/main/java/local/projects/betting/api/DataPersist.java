package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

public interface DataPersist {
	void persistFixtures(Map<Integer, Fixture> fixtures);

	void persistOdds(Map<Integer, Odds> odds);

	void persistResults(Map<Integer, Result> scores);

}
