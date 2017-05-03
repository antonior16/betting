package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.model.Fixture;

public interface OddsDataEntry extends DataEntry {
  public Map<Integer,Fixture> extractOdds();
}
