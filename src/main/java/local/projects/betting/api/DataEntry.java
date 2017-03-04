package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

public interface DataEntry {
  public Map<Integer, Result> extractResults(String timeFrame);
  
  public Map<Integer, Odds> extractOdds();
}
