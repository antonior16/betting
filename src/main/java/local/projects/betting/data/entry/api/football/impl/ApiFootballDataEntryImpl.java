package local.projects.betting.data.entry.api.football.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import local.projects.betting.api.OddsDataEntry;
import local.projects.betting.api.ScoreDataEntry;
import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.data.entry.api.football.model.Timeframe;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

public class ApiFootballDataEntryImpl implements OddsDataEntry, ScoreDataEntry {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiFootballDataEntryImpl.class);
  String oddsScoresResource = "http://api.football-data.org/v1/fixtures?timeFrame=";
  
  protected String YESTERDAY_SCORES = "p1";
  protected String TIMED_ODDS = "n2";
  
  private Fixture[] extractFixtures(String timeFrame) {
    Fixture[] fixtures = null;
    ObjectMapper mapper = new ObjectMapper();
    try {
      URL url = new URL(oddsScoresResource + timeFrame);
      // Convert JSON string from file to Object
      Timeframe timeframe = mapper.readValue(url, Timeframe.class);
      fixtures = timeframe.getFixtures();
      
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fixtures;
  }
  
  @Override
  public Map<Integer, Odds> extractOdds() {
    Map<Integer, Odds> odds = null;
    Fixture[] fixtures = extractFixtures(TIMED_ODDS);
    if (fixtures != null && fixtures.length > 0) {
      odds = new HashMap<Integer, Odds>();
      for (int i = 0; i < fixtures.length; i++) {
        Fixture fixture = fixtures[i];
        if (fixture.getOdds() != null && fixture.getStatus().equalsIgnoreCase("TIMED")) {
          LOGGER.debug("Adding " + fixture.getHomeTeamName().getName());
          odds.put(new Integer(i + 1),
              new Odds(fixture.getDate(),new Team(fixture.getHomeTeamName().getName()), new Team(fixture.getAwayTeamName().getName()),
                  fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(), fixture.getOdds().getAwayWin()));
        }
      }
    }
    return odds;
  }
  
  @Override
  public Map<Integer, Result> extractResults(String timeFrame) {
    Map<Integer, Result> results = null;
    Fixture[] fixtures = extractFixtures(TIMED_ODDS);
    if (fixtures != null && fixtures.length > 0) {
      results = new HashMap<Integer, Result>();
      for (int i = 0; i < fixtures.length; i++) {
        Fixture fixture = fixtures[i];
        if (fixture.getStatus().equalsIgnoreCase("FINISHED"))
          results.put(new Integer(i + 1),
              new Result(fixture.getDate(),new Team(fixture.getHomeTeamName().getName()), new Team(fixture.getAwayTeamName().getName()),
                  fixture.getResult().getGoalsHomeTeam(), fixture.getResult().getGoalsAwayTeam()));
      }
    }
    return results;
  }
}
