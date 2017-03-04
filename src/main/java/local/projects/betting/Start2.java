package local.projects.betting;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.DataEntry;
import local.projects.betting.api.DataPersist;
import local.projects.betting.data.entry.api.football.impl.ApiFootballDataEntryImpl;
import local.projects.betting.data.entry.snai.impl.SnaiOddsDataEntryImpl;
import local.projects.betting.data.persist.excel.impl.ExcelDataPersistImpl;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

/**
 * Hello world!
 */
public class Start2 {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Start2.class);
  private String fileName;
  private String sheetName;
  
  public String getFileName() {
    return fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  /**
   * @return the sheetName
   */
  public String getSheetName() {
    return sheetName;
  }
  
  /**
   * @param sheetName
   *          the sheetName to set
   */
  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }
  
  public static void main(String[] args) {
    Start2 s = new Start2();
    s.setFileName("E:/betting/Scommesse.xlsx");
    s.setSheetName("Risultati");
    
    // Getting DataEntry and DataPersist instance to extract Odds and
    // Fixtures
    DataEntry dataEntry = new SnaiOddsDataEntryImpl();
    DataPersist dataPersistResults = new ExcelDataPersistImpl(s.getFileName(), s.getSheetName());
    
    Map<Integer, Result> scores = dataEntry.extractResults("p2");
    
    if (scores != null && !scores.isEmpty()) {
      dataPersistResults.persistResults(scores);
    }
    
    s.setSheetName("Quote");
    DataPersist dataPersistOdds = new ExcelDataPersistImpl(s.getFileName(), s.getSheetName());
    
    Map<Integer, Odds> extractOdds = dataEntry.extractOdds();
    if (extractOdds != null && !extractOdds.isEmpty()) {
      dataPersistOdds.persistOdds(extractOdds);
    }
  }
}
