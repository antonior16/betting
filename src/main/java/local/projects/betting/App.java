package local.projects.betting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.League;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

/**
 * Hello world!
 */
public class App {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  
  private WebDriver driver;
  private List<League> leagues;
  private Map<Integer, Odds> data;
  private Map<Integer, Result> scoreData;
  
  private int start = 1;
  
  // Scores datasource
  private static final String SCORES_URL = "http://www.diretta.it";
  
  // Populate Excel Constants
  private static final String FILE_NAME = "C:/git/betting/Scommesse.xlsx";
  private static final String ODDS_SHEET = "Quote";
  private static final String SCORES_SHEET = "Risultati";
  
  // WEB Drivers Paths
  private static final String CHROME_DRIVER = "/Users/antonio/Desktop/chromedriver/chromedriver.exe";
  private static final String PHANTHOMJS_DRIVER = "src/main/resources/phantomjs-2.1.1-windows/bin/phantomjs.exe";
  
  public App(WebDriverEnum webDriver) {
    setWebDriver(0, 0, webDriver);
    leagues = new ArrayList<League>();
    
  }
  
  public static void main(String[] args) throws Exception {
    LOGGER.info("Hello World!");
    App a = new App(WebDriverEnum.PHANTOMJS);
    try {
//      a.extractOdds();
      
       a.extractScores();
      
      // a.populateExcel();
      // Close the browser
    } catch (Exception e) {
      
      LOGGER.info(e.getMessage());
    } finally {
      a.driver.quit();
    }
    
  }
  
  public void extractOdds() throws Exception {
    data = new TreeMap<Integer, Odds>();
    populateLeagues();
    
    for (League league : leagues) {
      driver.get(league.getOddsUrl());
      // And now use this to visit Google
      LOGGER.info("League: " + league.getName());
      // Check the title of the page
      LOGGER.info("Page title is: " + driver.getTitle());
      scrapeOddss();
    }
    
    if (data != null && !data.isEmpty()) {
      populateOddsSheet();
    }
  }
  
  public void extractScores() throws Exception {
    driver.get(SCORES_URL);
    // Check the title of the page
    LOGGER.info("Page title is: " + driver.getTitle());
    scrapeScores();
    
    if (!scoreData.isEmpty()) {
      populateScoresSheet();
    }
  }
  
  private void setWebDriver(int windowWidth, int windowHeight, WebDriverEnum webDriver) {
    String path = null;
    switch (webDriver) {
      case CHROME:
        path = CHROME_DRIVER;
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        driver = new ChromeDriver();
        break;
      case PHANTOMJS: // DesiredCapabilities caps =
        // DesiredCapabilities.phantomjs();
        // caps.setCapability(
        // PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
        // phantomJsBinaryPath);
        path = PHANTHOMJS_DRIVER;
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path);
        driver = new PhantomJSDriver();
    }
  }
  
  private void scrapeOddss() throws FileNotFoundException, IOException {
    // simplified: find table which contains the keyword
    // driver.findElement(By.linkText("calcio")).click();
    // driver.findElement(By.linkText("OGGI")).click();
    
    // driver.findElement(By.cssSelector("#CALCIO_0 > div:nth-child(1) >
    // div:nth-child(1) > a:nth-child(1)")).click();
    // driver.findElement(By.xpath("id('CALCIO_0')/x:div/x:div[1]/x:a[1]")).click();
    WebElement tableElement = driver.findElement(By.tagName("table"));
    
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
      for (int i = 1; i < userTable.size(); i++) {
        try {
          String match = userTable.get(i).get("1X2 FINALE,U/O 2,5,GOL NO GOL").getText();
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
          data.put(i, new Odds(new Date(2017,25,03),team1, team2, home, draw, away, under, over, gol, noGol));
        } catch (ParseException e) {
          LOGGER.error("An exception has occured parsing string value ", e);
        }
      }
    }
    
  }
  
  private void populateOddsSheet() throws FileNotFoundException, IOException {
    // private void populateExcel() {
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_NAME));
    // int index = workbook.getSheetIndex("Quote");
    // workbook.removeSheetAt(index);
    
    XSSFSheet sheet = workbook.getSheet(ODDS_SHEET);
    // "Partita", "1", "X", "2", "Under", "Over", "Gol", "No Gol"
    
    // Iterate over data and write to sheet
    Set<Integer> keyset = data.keySet();
    int rownum = 0;
    for (Integer key : keyset) {
      Row row = sheet.createRow(rownum++);
      Odds objArr = data.get(key);
      
      // Writing Cells for Odds Sheet
      Cell cell = row.createCell(0);
      
      cell.setCellValue(objArr.getHomeWin());
      
      cell = row.createCell(1);
      cell.setCellValue(objArr.getDraw());
      
      cell = row.createCell(2);
      cell.setCellValue(objArr.getAwayWin());
      
      cell = row.createCell(3);
      cell.setCellValue(objArr.getNoGol());
      
      cell = row.createCell(4);
      cell.setCellValue(objArr.getGol());
      
      cell = row.createCell(5);
      cell.setCellValue(objArr.getUnder());
      
      cell = row.createCell(6);
      cell.setCellValue(objArr.getOver());
    }
    
    // Writing in Excel
    try {
      // Write the workbook in file system
      FileOutputStream out = new FileOutputStream(FILE_NAME);
      workbook.write(out);
      out.close();
      LOGGER.info("Oddss have been writed in: " + FILE_NAME);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    LOGGER.info("Done");
  }
  
  private void scrapeScores() throws FileNotFoundException, IOException {
    driver.findElement(By.cssSelector(".yesterday")).click();
    driver.findElement(By.linkText("Conclusi")).click();
    List<WebElement> livescoreTables = driver.findElements(By.tagName("table"));
    scoreData = new HashMap<Integer, Result>();
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
    try {
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
    } catch (Exception e) {
      LOGGER.error("" + e);
    }
    
    for (int i = 0; i < userTable.size(); i++) {
      String score = userTable.get(i).get("score").getText();
      if (score != null && "Finale".equalsIgnoreCase(userTable.get(i).get("timer").getText())) {
        String time = userTable.get(i).get("time").getText();
        Team home = new Team(userTable.get(i).get("home").getText());
        Team away = new Team(userTable.get(i).get("away").getText());
        
        Integer goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf("-") - 1));
        Integer goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf("-") + 2, score.length()));
        scoreData.put(i + start, new Result(new Date(), home, away, goalsHomeTeam, goalsAwayTeam));
        LOGGER.debug(home.getName() + " - " + away.getName() + " score has been added ");
      }
      
      // Populating Oddss Map to write in data model (e.g excel)
    }
    LOGGER.info("Done");
  }
  
  public void selectPhoneType(String option) {
    // Open the dropdown so the options are visible
    // Get all of the options
    List<WebElement> options = driver.findElements(((By.xpath("//ul//li[contains(.,\"Oggi\")]"))));
    // Loop through the options and select the one that matches
    String a = null;
    for (WebElement opt : options) {
      a = opt.findElement(By.xpath("//a")).getText();
      System.out.println(a);
      //
      // if ( "OGGI".equals(a)) {
      // // opt.click();
      // System.out.println(a);
      // return;
      // }
    }
    throw new NoSuchElementException("Can't find " + option + " in dropdown");
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
      Result objArr = scoreData.get(key);
      
      // Writing Cells for Odds Sheet
      Cell cell = row.createCell(0);
      cell.setCellValue(objArr.getHomeTeamName().getName());
      
      cell = row.createCell(1);
      cell.setCellValue(objArr.getAwayTeamName().getName());
      
      cell = row.createCell(2);
      cell.setCellValue(objArr.getGoalsHomeTeam() + ":" + objArr.getGoalsAwayTeam());
    }
    
    // Writing in Excel
    try {
      // Write the workbook in file system
      FileOutputStream out = new FileOutputStream(FILE_NAME);
      workbook.write(out);
      out.close();
      LOGGER.info("Scores have been writed in: " + FILE_NAME);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    LOGGER.info("Done");
  }
  
  private void populateLeagues() {
    // Creating Leagues
    League serieA = new League("Serie A", "https://www.snai.it/sport/CALCIO/SERIE%20A");
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
