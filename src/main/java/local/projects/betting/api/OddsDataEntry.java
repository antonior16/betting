package local.projects.betting.api;

import java.util.Map;

import local.projects.betting.model.Odds;

public interface OddsDataEntry extends DataEntry {
  public Map<Integer,Odds> extractOdds();
}
