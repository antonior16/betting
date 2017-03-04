package local.projects.betting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

/**
 * Hello world!
 */
public class SeleniumApp {
  
  private WebDriver driver;
  private Map<String, String> leagues;
  private Map<Integer, Object[]> data;
  private int start = 1;
  
  public static void main(String[] args) throws Exception {
    System.out.println("Hello World!");
    SeleniumApp a = new SeleniumApp();
    
    // Diventa una lista di League
    a.leagues = new LinkedHashMap<String, String>();
    
    // a.leagues.put("Serie B", "https://www.snai.it/sport/CALCIO/SERIE%20B");
    // a.leagues.put("Serie A", "https://www.snai.it/sport/CALCIO/SERIE%20A");
    // a.leagues.put("Liga", "https://www.snai.it/sport/CALCIO/LIGA%20SPAGNOLA");
    // a.leagues.put("Ligue 1", "https://www.snai.it/sport/CALCIO/LIGUE%201");
    // a.leagues.put("Premier League", "https://www.snai.it/sport/CALCIO/PREMIER%20LEAGUE");
    // a.leagues.put("Bundesliga", "https://www.snai.it/sport/CALCIO/BUNDESLIGA");
    // a.leagues.put("Olanda", "https://www.snai.it/sport/CALCIO/OLANDA%201");
    // a.leagues.put("Germania 2", "https://www.snai.it/sport/CALCIO/GERMANIA%202");
    // a.leagues.put("Ligue 2", "https://www.snai.it/sport/CALCIO/FRANCIA%202");
    // a.leagues.put("Portogallo", "https://www.snai.it/sport/CALCIO/PORTOGALLO%201");
    
    a.leagues.put("Risultati", "http://www.diretta.it");
    
    a.data = new TreeMap<Integer, Object[]>();
    // a.data.put(1, new Object[] { "Partita", "1", "X", "2", "Under", "Over", "Gol", "No Gol" });
    a.data.put(1, new Object[] { "Home", "Away", "Score" });
    
    a.scrape();
    
    // a.populateExcel();
  }
  
  public void scrape() throws Exception {
    
    driver = getWebDriver(0, 0, "PHANTOM_JS");
    
    for (Map.Entry<String, String> entry : leagues.entrySet()) {
      driver.get(entry.getValue());
      // And now use this to visit Google
      System.out.println("League: " + entry.getValue());
      // Check the title of the page
      System.out.println("Page title is: " + driver.getTitle());
      extractScores();
    }
    // Close the browser
    driver.quit();
  }
  
  private RemoteWebDriver getWebDriver(int windowWidth, int windowHeight, String webDriver) {
    RemoteWebDriver driver = null;
    String path = null;
    switch (webDriver) {
      case "CHROME":
        path = "/Users/antonio/Desktop/chromedriver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        break;
      case "PHANTOM_JS": // DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        // caps.setCapability(
        // PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
        // phantomJsBinaryPath);
        path = "/Users/antonio/Downloads/phantomjs-2.1.1-windows/bin/phantomjs.exe";
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path);
        driver = new PhantomJSDriver();
    }
    // driver.manage().window().setSize(new Dimension(windowWidth, windowHeight));
    return driver;
  }
  
  private void extractBookmakers() throws FileNotFoundException, IOException {
    // simplified: find table which contains the keyword
    WebElement tableElement = driver.findElement(By.xpath(".//table"));
    
    // create empty table object and iterate through all rows of the found table element
    ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
    List<WebElement> rowElements = tableElement.findElements(By.xpath(".//tr"));
    
    // get column names of table from table headers
    ArrayList<String> columnNames = new ArrayList<String>();
    List<WebElement> headerElements = rowElements.get(0).findElements(By.xpath(".//th"));
    for (WebElement headerElement : headerElements) {
      columnNames.add(headerElement.getText());
    }
    
    // iterate through all rows and add their content to table array
    for (WebElement rowElement : rowElements) {
      HashMap<String, WebElement> row = new HashMap<String, WebElement>();
      
      // add table cells to current row
      int columnIndex = 0;
      List<WebElement> cellElements = rowElement.findElements(By.xpath(".//td"));
      for (WebElement cellElement : cellElements) {
        row.put(columnNames.get(columnIndex), cellElement);
        columnIndex++;
      }
      
      userTable.add(row);
    }
    
    for (int i = 1; i < userTable.size(); i++) {
      String match = userTable.get(i).get("1X2 FINALE,U/O 2,5,GOL NO GOL").getText();
      String home = userTable.get(i).get("1").getText();
      String draw = userTable.get(i).get("X").getText();
      String away = userTable.get(i).get("2").getText();
      String under = userTable.get(i).get("UNDER").getText();
      String over = userTable.get(i).get("OVER").getText();
      String gol = userTable.get(i).get("GOL").getText();
      String noGol = userTable.get(i).get("NOGOL").getText();
      
      data.put(i + start, new Object[] { match, home, draw, away, under, over, gol, noGol });
    }
    System.out.println("Done");
  }
  
  private void populateExcel() throws FileNotFoundException, IOException {
    // private void populateExcel() {
    
    String FILE_NAME = "/Users/antonio/Desktop/Scommesse.xlsx";
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_NAME));
    // int index = workbook.getSheetIndex("Quote");
    // workbook.removeSheetAt(index);
    
    XSSFSheet sheet = workbook.getSheet("Quote");
    
    // This data needs to be written (Object[])
    // Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
    // data.put(1, new Object[] { "PARTITA", "1", "X", "2" });
    // data.put(2, new Object[] { "Rennes - Nizza", "2,70", "2,10", "3,30" });
    
    // Iterate over data and write to sheet
    Set<Integer> keyset = data.keySet();
    int rownum = 0;
    for (Integer key : keyset) {
      Row row = sheet.createRow(rownum++);
      Object[] objArr = data.get(key);
      int cellnum = 0;
      for (Object obj : objArr) {
        Cell cell = row.createCell(cellnum++);
        if (obj instanceof String)
          cell.setCellValue((String) obj);
        else if (obj instanceof Integer)
          cell.setCellValue((Integer) obj);
      }
    }
    
    try {
      // Write the workbook in file system
      FileOutputStream out = new FileOutputStream(FILE_NAME);
      workbook.write(out);
      out.close();
      System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println("Done");
  }
  
  private void extractScores() throws FileNotFoundException, IOException {
    
    List<String> fields = new ArrayList<String>();
    fields.add("time");
    fields.add("playingTime");
    fields.add("home");
    fields.add("score");
    fields.add("away");
    fields.add("live");
    fields.add("info1");
    fields.add("info2");
    
    // simplified: find table which contains the keyword
    List<WebElement> tableElements = driver.findElements(By.xpath(".//table/tbody"));
    
    // create empty table object and iterate through all rows of the found table element
    ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
    
    // if tbody exists
    if (tableElements != null && !tableElements.isEmpty()) {
      System.out.println("Tabelle " + tableElements.size());
      // iterate tbody
      HashMap<String, WebElement> row = new HashMap<String, WebElement>();
      
      for (WebElement tableElement : tableElements) {
        // for each tr in tbody iterate td
        List<WebElement> rowElements = tableElement.findElements(By.xpath(".//tr"));
        // iterate through all rows and add their content to table array
        
        for (WebElement rowElement : rowElements) {
          int columnIndex = 0;
          List<WebElement> cellElements = rowElement.findElements(By.xpath(".//td"));
          for (WebElement cellElement : cellElements) {
            row.put(fields.get(columnIndex), cellElement);
            columnIndex++;
          }
          // System.out.println(row.get("home").getText());
          userTable.add(row);
        }
        
        System.out.println("User Table size: " + userTable.size());
        // System.out.println("End: " + tableElement.getText());
        break;
      }
      
      for (int i = 0; i < userTable.size(); i++) {
        String home = userTable.get(i).get("home").getText();
        String score = userTable.get(i).get("score").getText();
        String away = userTable.get(i).get("away").getText();
        
        // System.out.println(home + " " + away + " " + score);
        System.out.println(home);
        // data.put(i + start, new Object[] { home, away, score });
      }
      System.out.println("Done");
    }
  }
}
