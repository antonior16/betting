package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.model.Fixture;

public interface DataEntry {
  public Map<Integer, Fixture> extractResults(String timeFrame);
  public Map<Integer, Fixture> extractOdds();
}
