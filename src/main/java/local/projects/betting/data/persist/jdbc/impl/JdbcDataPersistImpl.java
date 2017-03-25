package local.projects.betting.data.persist.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import local.projects.betting.api.DataPersist;
import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

@Component
public class JdbcDataPersistImpl implements DataPersist {
  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDataPersistImpl.class);
  @Autowired
  private JdbcTemplate jdbcTemplate;
  private String result;
  private String goalNoGol;
  private String underOver;
  
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  private void buildResult(int goalsHomeTeam, int goalsAwayTeam) {
    if (goalsHomeTeam > goalsAwayTeam) {
      result = "1";
    } else {
      result = "2";
    }
    
    if ((goalsHomeTeam - goalsAwayTeam) == 0) {
      result = "X";
    }
    
    // Setting Gol/NoGol
    
    if (goalsHomeTeam > 0 & goalsAwayTeam > 0) {
      goalNoGol = "GOL";
    } else {
      goalNoGol = "NOGOL";
    }
    
    // Setting Under/Over
    if (goalsHomeTeam + goalsAwayTeam >= 3) {
      underOver = "OVER";
    } else {
      underOver = "UNDER";
    }
  }
  
  public void persistOdds(Map<Integer, Odds> odds) {
    Set<Integer> keyset = odds.keySet();
    for (Integer key : keyset) {
      final Odds objArr = odds.get(key);
      
      // Saving only match having Odds
      
      LOGGER.debug(objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
      
      if (objArr.getDate() == null) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String oddsDate = df.format(new Date()).toString();
      }
      
      String SQL =
          "insert into partite (Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      LOGGER.info(objArr.toString());
      jdbcTemplate.update(SQL, objArr.getHomeTeamName().getName(), objArr.getAwayTeamName().getName(),
          objArr.getHomeWin(),
          objArr.getDraw(), objArr.getAwayWin(), objArr.getUnder(), objArr.getOver(), objArr.getGol(),
          objArr.getNoGol());
      LOGGER.info("Created Record Name = " + objArr.getHomeTeamName() + " Age = " + objArr.getAwayTeamName());
    }
    return;
  }
  
  @Override
  public void persistFixtures(Map<Integer, Fixture> fixtures) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void persistResults(Map<Integer, Result> scores) {
    // TODO Auto-generated method stub
  }
}
