package local.projects.betting.api.data.extract;

import java.util.List;
import java.util.Map;

import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;

public interface OddsDataExtract extends DataExtract {
  public List<Fixture> extractOdds(League league);
}
