package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.model.Fixture;

public interface ScoreDataEntry extends DataEntry {
  public Map<Integer, Fixture> extractResults(String timeFrame);
}
