package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.model.Result;

public interface ScoreDataEntry extends DataEntry {
  public Map<Integer, Result> extractResults(String timeFrame);
}
