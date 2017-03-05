package local.projects.betting.data.entry.api.football.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
  String fixturesResourceUrl = "http://api.football-data.org/v1/fixtures?timeFrame=";
  
  protected String YESTERDAY_SCORES = "p1";
  protected String TIMED_ODDS = "n2";
  
  public Map<Integer, Fixture> extractFixtures(String timeFrame) {
    Map<Integer, Fixture> result = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      URL url = new URL(fixturesResourceUrl + timeFrame);
      // Convert JSON string from file to Object
      Timeframe timeframe = mapper.readValue(url, Timeframe.class);
      Fixture[] fixtures = timeframe.getFixtures();
      
      for (int i = 0; i < fixtures.length; i++) {
        Fixture fixture = fixtures[i];
        LOGGER.debug("Adding " + fixture.getHomeTeamName().getName());
        result.put(new Integer(i + 1), fixture);
      }
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
  
  @Override
  public Map<Integer, Odds> extractOdds() {
    Map<Integer, Odds> odds = null;
    Map<Integer, Fixture> fixturesMap = extractFixtures(TIMED_ODDS);
    
    // checking for fixtures
    if (fixturesMap != null && fixturesMap.size() > 0) {
      odds = new HashMap<Integer, Odds>();
      // extracting fixtures from result map
      for (Entry<Integer, Fixture> entry : fixturesMap.entrySet()) {
        Integer key = entry.getKey();
        Fixture fixture = entry.getValue();
        
        // if extraxcted fixture contians odds add to odds result map else continue to next entry
        if (fixture.getOdds() != null) {
          Odds odd = new Odds(fixture.getDate(), new Team(fixture.getHomeTeamName().getName()),
              new Team(fixture.getAwayTeamName().getName()),
              fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(), fixture.getOdds().getAwayWin());
          odds.put(key, odd);
        } else {
          continue;
        }
      }
    }
    return odds;
  }
  
  @Override
  public Map<Integer, Result> extractResults(String timeFrame) {
    Map<Integer, Result> results = new HashMap<Integer, Result>();
    Map<Integer, Fixture> fixturesMap = extractFixtures(timeFrame);
    
    if (fixturesMap != null && fixturesMap.size() > 0) {
      
      for (Entry<Integer, Fixture> entry : fixturesMap.entrySet()) {
        // Extract Fixtures form result map
        Integer key = entry.getKey();
        Fixture fixture = entry.getValue();
        Result result = new Result(fixture.getDate(), new Team(fixture.getHomeTeamName().getName()),
            new Team(fixture.getAwayTeamName().getName()),
            fixture.getResult().getGoalsHomeTeam(), fixture.getResult().getGoalsAwayTeam());
        results.put(key, result);
      }
    }
    
    return results;
  }
}
