package local.projects.betting.data.entry.diretta.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.App;
import local.projects.betting.api.ScoreDataEntry;
import local.projects.betting.data.entry.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.League;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

/**
 * Hello world!
 */
public class DirettaScoresDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl implements ScoreDataEntry {
  public DirettaScoresDataEntryImpl(WebDriverEnum webDriver) {
    super(webDriver);
    driver.get(SCORES_URL);
    // Check the title of the page
    LOGGER.info("Page title is: " + driver.getTitle());
  }
  
  private static final Logger LOGGER = LoggerFactory.getLogger(DirettaScoresDataEntryImpl.class);
  
  // Scores datasource
  private static final String SCORES_URL = "http://www.diretta.it";
  
  @Override
  public Map<Integer, Odds> extractOdds() {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Map<Integer, Result> extractResults(String timeFrame) {
    Map<Integer, Result> result = new HashMap<Integer, Result>();
    try{
    driver.findElement(By.cssSelector(".yesterday")).click();
    driver.findElement(By.linkText("Conclusi")).click();
    List<WebElement> livescoreTables = driver.findElements(By.tagName("table"));
    List<String> fields = new ArrayList<String>();
    fields.add("time");
    fields.add("timer");
    fields.add("home");
    fields.add("score");
    fields.add("away");
    fields.add("top");
    fields.add("space1");
    fields.add("space2");
    
    // create empty table object and iterate through all rows of the found
    // table element
    ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
      if (!livescoreTables.isEmpty()) {
        
        for (WebElement tableElement : livescoreTables) {
          WebElement tbody = tableElement.findElement(By.tagName("tbody"));
          
          List<WebElement> rowElements = tbody.findElements(By.tagName("tr"));
          
          // iterate through all rows and add their content to table array
          for (WebElement rowElement : rowElements) {
            HashMap<String, WebElement> row = new HashMap<String, WebElement>();
            
            // add table cells to current row
            int columnIndex = 0;
            List<WebElement> cellElements = rowElement.findElements(By.tagName("td"));
            for (WebElement cellElement : cellElements) {
              System.out.println("--------->" + fields.get(columnIndex) + " : " + cellElement.getText());
              row.put(fields.get(columnIndex), cellElement);
              columnIndex++;
            }
            userTable.add(row);
          }
        }
      }
    
    
    if (!userTable.isEmpty()) {
      for (int i = 0; i < userTable.size(); i++) {
        String score = userTable.get(i).get("score").getText();
        if (score != null && "Finale".equalsIgnoreCase(userTable.get(i).get("timer").getText())) {
          int record = i++;
          String time = userTable.get(i).get("time").getText();
          Team home = new Team(userTable.get(i).get("home").getText());
          Team away = new Team(userTable.get(i).get("away").getText());
          
          Integer goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf("-") - 1));
          Integer goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf("-") + 2, score.length()));
          // Populating Oddss Map to write in data model (e.g excel)
          result.put(record, new Result(new Date(), home, away, goalsHomeTeam, goalsAwayTeam));
          LOGGER.debug(home.getName() + " - " + away.getName() + " score has been added ");
        }
      }
    } else {
      LOGGER.info("No data found from scores provider");
    }
    LOGGER.info("Done");
    }catch(Exception e){
      LOGGER.error("An Excetpion has occurred extracting scores ",e);
    }finally{
      driver.quit();
    }
    
    return result;
  }
}
