package local.projects.betting.data.entry.snai.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.League;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Team;

public class SnaiOddsDataEntryImpl extends AbstractSnaiDataEntryImpl {
  private static final Logger LOGGER = LoggerFactory.getLogger(SnaiOddsDataEntryImpl.class);
  private List<League> leagues;
  private Date oddsDate;
  
  public SnaiOddsDataEntryImpl(WebDriverEnum webDriver) {
    super(webDriver);
    populateLeagues();
  }
  
  @Override
  public Map<Integer, Odds> extractOdds() {
    Map<Integer, Odds> result = new HashMap<Integer, Odds>();
    if (!leagues.isEmpty()) {
      for (League league : leagues) {
        driver.get(league.getOddsUrl());
        // And now use this to visit Google
        LOGGER.info("League: " + league.getName());
        // Check the title of the page
        LOGGER.info("Page title is: " + driver.getTitle());
        try {
          WebDriverWait wait = new WebDriverWait(driver, 10);
          wait.until(ExpectedConditions.elementToBeClickable(By.linkText("calcio"))).click();
          // driver.findElement(By.linkText("calcio")).click();
          
          // driver.findElement(By.linkText("OGGI")).click();
          wait.until(ExpectedConditions.elementToBeClickable(By.linkText("OGGI"))).click();
          
          // WebElement tableElement = driver.findElement(By.tagName("table"));
          
          WebElement tableElement = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("table")));
          
          // create empty table object and iterate through all rows of the found
          // table element
          ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
          List<WebElement> rowElements = tableElement.findElements(By.tagName("tr"));
          
          // get column names of table from table headers
          ArrayList<String> columnNames = new ArrayList<String>();
          List<WebElement> headerElements = rowElements.get(0).findElements(By.tagName("th"));
          for (WebElement headerElement : headerElements) {
            columnNames.add(headerElement.getText());
          }
          
          // iterate through all rows and add their content to table array
          for (WebElement rowElement : rowElements) {
            HashMap<String, WebElement> row = new HashMap<String, WebElement>();
            
            // add table cells to current row
            int columnIndex = 0;
            List<WebElement> cellElements = rowElement.findElements(By.tagName("td"));
            for (WebElement cellElement : cellElements) {
              row.put(columnNames.get(columnIndex), cellElement);
              columnIndex++;
            }
            
            userTable.add(row);
          }
          
          NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
          
          if (!userTable.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            
            String timeFrame = sdf.format(new Date()).toString();
            try {
              oddsDate = sdf.parse(timeFrame);
            } catch (ParseException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            
            for (int i = 1; i < userTable.size(); i++) {
              try {
                String match = userTable.get(i).get("1X2 FINALE,U/O 2,5,GOL NO GOL").getText();
                
                match = match.substring(match.indexOf("\n"), match.length());
                Double home = new Double(format.parse(userTable.get(i).get("1").getText()).doubleValue());
                Double draw = new Double(format.parse(userTable.get(i).get("X").getText()).doubleValue());
                Double away = new Double(format.parse(userTable.get(i).get("2").getText()).doubleValue());
                Double under = new Double(format.parse(userTable.get(i).get("UNDER").getText()).doubleValue());
                Double over = new Double(format.parse(userTable.get(i).get("OVER").getText()).doubleValue());
                Double gol = new Double(format.parse(userTable.get(i).get("GOL").getText()).doubleValue());
                Double noGol = new Double(format.parse(userTable.get(i).get("NOGOL").getText()).doubleValue());
                
                Team team1 = new Team(match.substring(6, match.indexOf("-") - 1).trim());
                Team team2 = new Team(match.substring(match.indexOf("-") + 1, match.length()).trim());
                
                // Populating Oddss Map to write in data model (e.g excel)
                result.put(i, new Odds(oddsDate, team1, team2, home, draw, away, under, over, gol, noGol));
              } catch (ParseException e) {
                LOGGER.error("An exception has occured parsing string value ", e);
              }
            }
          }
        } catch (Exception e) {
          LOGGER.error("An exception extracting odds ", e);
        } finally {
          driver.quit();
        }
        
      }
    }
    return result;
  }
  
  private void populateLeagues() {
    // Creating Leagues
    leagues = new ArrayList<League>();
    League serieA = new League("Serie A", "https://www.snai.it/sport");
    // League serieB = new League("Serie B",
    // "https://www.snai.it/sport/CALCIO/SERIE%20B");
    // League liga = new League("Liga",
    // "https://www.snai.it/sport/CALCIO/LIGA%20SPAGNOLA");
    // League ligue1 = new League("Ligue 1",
    // "https://www.snai.it/sport/CALCIO/LIGUE%201");
    // League premierLeague = new League("Premier League",
    // "https://www.snai.it/sport/CALCIO/PREMIER%20LEAGUE");
    // League bundesliga = new League("Bundesliga",
    // "https://www.snai.it/sport/CALCIO/BUNDESLIGA");
    // League olanda1 = new League("Olanda",
    // "https://www.snai.it/sport/CALCIO/OLANDA%201");
    // League germania2 = new League("Germania 2",
    // "https://www.snai.it/sport/CALCIO/GERMANIA%202");
    // League ligue2 = new League("Ligue 2",
    // "https://www.snai.it/sport/CALCIO/FRANCIA%202");
    // League portogallo1 = new League("Portogallo",
    // "https://www.snai.it/sport/CALCIO/PORTOGALLO%201");
    //
    leagues.add(serieA);
    // leagues.add(serieB);
    // leagues.add(liga);
    // leagues.add(ligue1);
    // leagues.add(premierLeague);
    // leagues.add(bundesliga);
    // leagues.add(olanda1);
    // leagues.add(germania2);
    // leagues.add(ligue2);
    // leagues.add(portogallo1);
  }
  
}
