package local.projects.betting.data.persist.jdbc.impl;

import java.sql.Types;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
  
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  public void persistOdds(Map<Integer, Odds> odds) {
    if (!odds.isEmpty()) {
      jdbcTemplate.update("DELETE FROM quote");
      DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
      Date oddsdate = odds.get(0).getOddsDate();
      String resultDate = df.format(oddsdate).toString();
      
      Set<Integer> keyset = odds.keySet();
      for (Integer key : keyset) {
        final Odds objArr = odds.get(key);
        
        // Saving only match having Odds
        
        LOGGER.debug(objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
        String SQL =
            "insert into quote (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        LOGGER.info(objArr.toString());
        jdbcTemplate.update(SQL, objArr.getOddsDate(), objArr.getHomeTeamName().getName(),
            objArr.getAwayTeamName().getName(), objArr.getHomeWin(), objArr.getDraw(), objArr.getAwayWin(),
            objArr.getUnder(), objArr.getOver(), objArr.getGol(), objArr.getNoGol());
        LOGGER.info("Created Record Home = " + objArr.getHomeTeamName() + " Away = " + objArr.getAwayTeamName()
            + "in quote");
        
        SQL =
            "insert into partite (Data_Partita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, objArr.getOddsDate(), objArr.getHomeTeamName().getName(),
            objArr.getAwayTeamName().getName(), objArr.getHomeWin(), objArr.getDraw(), objArr.getAwayWin(),
            objArr.getUnder(), objArr.getOver(), objArr.getGol(), objArr.getNoGol());
        LOGGER.info("Created Record Home = " + objArr.getHomeTeamName() + " Away = " + objArr.getAwayTeamName()
            + "in partite");
      }
      String updateSql = "update Leagues set last_odds_update = ? where league_id = ?";
      int[] types = { Types.DATE, Types.BIGINT };
      
      Object[] params = { resultDate, 1 };
      int rows = jdbcTemplate.update(updateSql, params, types);
      System.out.println(rows + " row(s) updated.");
    }
    return;
  }
  
  private String generateLastUpdateDate(Date date) {
    if (date == null) {
      date = new Date();
    }
    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    String timeFrame = sdf.format(date).toString();
    return timeFrame;
  }
  
  @Override
  public void persistFixtures(Map<Integer, Fixture> fixtures) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void persistResults(Map<Integer, Result> results) {
    if (!results.isEmpty()) {
      DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
      String resultDate = df.format(results.get(1).getOddsDate()).toString();
      Set<Integer> keyset = results.keySet();
      for (Integer key : keyset) {
        final Result objArr = results.get(key);
        
        // Saving only match having Odds
        LOGGER.debug(objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
        String SQL =
            "insert into risultati (data_partita,Casa, Trasferta, Risultato, Segno, gol_nogol, under_over,multigol_2_4, multigol_2_3) values (?, ?, ?, ?, ?, ?,?,?,?)";
        try {
          LOGGER.info(objArr.toString());
          jdbcTemplate.update(SQL, objArr.getOddsDate(), objArr.getHomeTeamName().getName(),
              objArr.getAwayTeamName().getName(), objArr.getSign(), objArr.getScore(),
              objArr.getGoalNoGol(), objArr.getUnderOver(), objArr.getIs2To4Multigol(),
              objArr.getIs2To3Multigol());
          LOGGER.info("Created Record Home = " + objArr.getHomeTeamName() + " Away = "
              + objArr.getAwayTeamName() + "in risultati");
        } catch (Exception e) {
          LOGGER.error("Error performing " + SQL, e);
        }
      }
      String updateSql = "update Leagues set last_results_update = ? where league_id = ?";
      int[] types = { Types.DATE, Types.BIGINT };
      
      Object[] params = { resultDate, 1 };
      int rows = jdbcTemplate.update(updateSql, params, types);
      System.out.println(rows + " row(s) updated.");
    }
    LOGGER.info("All results have been added");
    return;
  }
}
