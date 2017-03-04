package local.projects.betting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import local.projects.betting.api.DataEntry;
import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.data.entry.api.football.model.Timeframe;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Score;

public class ApiFootballApp {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiFootballApp.class);
  String oddsScoresResource = "http://api.football-data.org/v1/fixtures?timeFrame=";
  private Map<Integer, Fixture> scoreData;
  
  private static final String YESTERDAY_SCORES = "p1";
  private static final String TIMED_ODDS = "n1";
  
  private int start = 1;
  
  // Populate Excel Constants
  private static final String FILE_NAME = "E:/betting/Scommesse.xlsx";
  private static final String ODDS_SHEET = "Quote";
  private static final String SCORES_SHEET = "Risultati";
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    ApiFootballApp obj = new ApiFootballApp();
    obj.extractFixtures(YESTERDAY_SCORES);
    obj.populateScoresSheet();
    obj.extractFixtures(TIMED_ODDS);
    obj.populateOddsSheet();
  }
  
  private void extractFixtures(String timeFrame) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      URL url = new URL(oddsScoresResource + timeFrame);
      // Convert JSON string from file to Object
      Timeframe timeframe = mapper.readValue(url, Timeframe.class);
      
      Fixture[] fixtures = timeframe.getFixtures();
      
      if (fixtures != null && fixtures.length > 0) {
        scoreData = new HashMap<Integer, Fixture>();
        for (int i = 0; i < fixtures.length; i++) {
          Fixture fixture = fixtures[i];
          scoreData.put(i, fixtures[i]);
        }
      }
      
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void populateScoresSheet() throws FileNotFoundException, IOException {
    // private void populateExcel() {
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_NAME));
    // int index = workbook.getSheetIndex("Quote");
    // workbook.removeSheetAt(index);
    
    XSSFSheet sheet = workbook.getSheet(SCORES_SHEET);
    
    // Iterate over data and write to sheet
    Set<Integer> keyset = scoreData.keySet();
    int rownum = 0;
    for (Integer key : keyset) {
      Row row = sheet.createRow(rownum++);
      Fixture objArr = scoreData.get(key);
      try {
        // Saving only match having Odds
        if (objArr.getOdds() != null) {
          LOGGER.debug(objArr.getHomeTeamName().getName() + " : " + objArr.getAwayTeamName().getName());
          Cell cell = row.createCell(0);
          cell.setCellValue(objArr.getHomeTeamName().getName());
          
          cell = row.createCell(1);
          cell.setCellValue(objArr.getAwayTeamName().getName());
          
          cell = row.createCell(2);
          cell.setCellValue(objArr.getOdds().getHomeWin());
          
          cell = row.createCell(3);
          cell.setCellValue(objArr.getOdds().getDraw());
          
          cell = row.createCell(4);
          cell.setCellValue(objArr.getOdds().getAwayWin());
          
          // Scores only if match is finished
          if (objArr.getStatus().equalsIgnoreCase("FINISHED")) {
            
            int goalsHomeTeam = objArr.getResult().getGoalsHomeTeam();
            int goalsAwayTeam = objArr.getResult().getGoalsAwayTeam();
            
            // Setting Scores
            cell = row.createCell(5);
            cell.setCellValue(goalsHomeTeam + ":" + goalsAwayTeam);
            
            cell = row.createCell(6);
            
            if (goalsHomeTeam > goalsAwayTeam) {
              cell.setCellValue("1");
            } else {
              cell.setCellValue("2");
            }
            
            if ((goalsHomeTeam - goalsAwayTeam) == 0) {
              cell.setCellValue("X");
            }
            
            // Setting Gol/NoGol
            cell = row.createCell(7);
            if (goalsHomeTeam > 0 & goalsAwayTeam > 0) {
              cell.setCellValue("GOL");
            } else {
              cell.setCellValue("NOGOL");
            }
            
            // Setting Under/Over
            cell = row.createCell(8);
            if (goalsHomeTeam + goalsAwayTeam >= 3) {
              cell.setCellValue("OVER");
            } else {
              cell.setCellValue("UNDER");
            }
          }
          
        }
      } catch (Exception e) {
        LOGGER.error(
            "Errore -----> : " + objArr.getHomeTeamName().getName() + " : " + objArr.getAwayTeamName().getName());
        LOGGER.error(e.getCause().getMessage());
      }
    }
    // Writing in Excel
    try {
      // Write the workbook in file system
      FileOutputStream out = new FileOutputStream(FILE_NAME);
      workbook.write(out);
      out.close();
      LOGGER.info("Odds have been writed in: " + FILE_NAME);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("Done");
  }
  
  private void populateOddsSheet() throws FileNotFoundException, IOException {
    // private void populateExcel() {
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_NAME));
    // int index = workbook.getSheetIndex("Quote");
    // workbook.removeSheetAt(index);
    XSSFSheet sheet = workbook.getSheet(ODDS_SHEET);
    // Iterate over data and write to sheet
    Set<Integer> keyset = scoreData.keySet();
    int rownum = 0;
    for (Integer key : keyset) {
      Row row = sheet.createRow(rownum++);
      Fixture objArr = scoreData.get(key);
      
      // Saving only match having Odds
      if (objArr.getOdds() != null) {
        Cell cell = row.createCell(0);
        cell.setCellValue(objArr.getHomeTeamName().getName());
        
        cell = row.createCell(1);
        cell.setCellValue(objArr.getAwayTeamName().getName());
        
        cell = row.createCell(3);
        cell.setCellValue(objArr.getOdds().getHomeWin());
        
        cell = row.createCell(4);
        cell.setCellValue(objArr.getOdds().getDraw());
        
        cell = row.createCell(5);
        cell.setCellValue(objArr.getOdds().getAwayWin());
      }
    }
    
    // }
    
    // Writing in Excel
    try {
      // Write the workbook in file system
      FileOutputStream out = new FileOutputStream(FILE_NAME);
      workbook.write(out);
      out.close();
      LOGGER.info("Odds have been writed in: " + FILE_NAME);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("Done");
  }
}
