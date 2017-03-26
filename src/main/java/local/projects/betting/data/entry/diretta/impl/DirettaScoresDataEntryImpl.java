package local.projects.betting.data.entry.diretta.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.ScoreDataEntry;
import local.projects.betting.data.entry.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

/**
 * Hello world!
 */
public class DirettaScoresDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl implements ScoreDataEntry {
  
  private Date scoreDate;
  
  public DirettaScoresDataEntryImpl(WebDriverEnum webDriver) {
    super(webDriver);
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
    ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
    try {
      // for (League league : leaguesList.getLeagues()) {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      driver.get(SCORES_URL);
      // Check the title of the page
      LOGGER.info("Page title is: " + driver.getTitle());
      
      
      
      for (int i = 0; i < getDiffDays(timeFrame); i++) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("yesterday"))).click();
      }
      
      wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Conclusi"))).click();
      // driver.findElement(By.linkText("Conclusi")).click();
      
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
      
      // create empty table object and iterate through all rows of the
      // found
      // table element
      if (!livescoreTables.isEmpty()) {
        
        for (WebElement tableElement : livescoreTables) {
          WebElement tbody = tableElement.findElement(By.tagName("tbody"));
          
          List<WebElement> rowElements = tbody.findElements(By.tagName("tr"));
          // int rowId = 0;
          for (WebElement rowElement : rowElements) {
            // rowId++;
            // if (rowId == 1) {
            // continue;
            // }
            
            // if ("event_round".equalsIgnoreCase(rowElement.getAttribute("class"))) {
            // break;
            // }
            HashMap<String, WebElement> row = new HashMap<String, WebElement>();
            
            // add table cells to current row
            int columnIndex = 0;
            List<WebElement> cellElements = rowElement.findElements(By.tagName("td"));
            for (WebElement cellElement : cellElements) {
              System.out.println(
                  "--------->" + fields.get(columnIndex) + " : " + cellElement.getText());
              row.put(fields.get(columnIndex), cellElement);
              columnIndex++;
            }
            userTable.add(row);
          }
        }
      }
      // }
      
      if (!userTable.isEmpty()) {
        int record = 0;
        for (int i = 0; i < userTable.size(); i++) {
          String score = userTable.get(i).get("score").getText();
          if (score != null) {
            
            if (score != null &&
                "Finale".equalsIgnoreCase(userTable.get(i).get("timer").getText())) {
              String time = userTable.get(i).get("time").getText();
              Team home = new Team(userTable.get(i).get("home").getText());
              Team away = new Team(userTable.get(i).get("away").getText());
              LOGGER.info("----------->" + home.getName());
              Integer goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf("-") - 1));
              Integer goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf("-") + 2, score.length()));
              // Populating Oddss Map to write in data model (e.g
              // excel)
              
              result.put(++record, new Result(scoreDate, home, away, goalsHomeTeam, goalsAwayTeam));
              LOGGER.debug(home.getName() + " - " + away.getName() + " score has been added ");
            }
          }
        }
      } else {
        LOGGER.info("No data found from scores provider");
      }
    } catch (Exception e) {
      LOGGER.error("An Excetpion has occurred extracting scores ", e);
    } finally {
      driver.quit();
    }
    LOGGER.info("Done");
    
    return result;
  }
  
  private long getDiffDays(String timeFrame) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
    long dayDiff = 0;
    try {
      scoreDate = sdf.parse(timeFrame);
    } catch (ParseException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
    long diff = new Date().getTime() - scoreDate.getTime();
    dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    
    return dayDiff;
  }
}
