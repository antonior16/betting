package local.projects.betting.api.data.extract;

import java.util.Map;

import local.projects.betting.model.Fixture;

public interface OddsDataExtract extends DataExtract {
  public Map<Integer,Fixture> extractOdds();
}
