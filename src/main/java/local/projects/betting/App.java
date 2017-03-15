package local.projects.betting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	private static final String SCORES_URL = "http://www.direttagoal.it";

	// Populate Excel Constants
	private static final String FILE_NAME = "C:/git/betting/Scommesse.xlsx";
	private static final String OddsS_SHEET = "Quote";
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
			// a.extractOddss();

			a.extractScores();

			// a.populateExcel();
			// Close the browser
		} catch (Exception e) {

			LOGGER.info(e.getMessage());
		} finally {
			a.driver.quit();
		}

	}

	public void extractOddss() throws Exception {
		data = new TreeMap<Integer, Odds>();
		populateLeagues();

		for (League league : leagues) {
			driver.get(league.getBooksUrl());
			// And now use this to visit Google
			LOGGER.info("League: " + league.getName());
			// Check the title of the page
			LOGGER.info("Page title is: " + driver.getTitle());
			scrapeOddss();
		}

		if (data != null && !data.isEmpty()) {
			populateOddssSheet();
		}
	}

	public void extractScores() throws Exception {
		driver.get(SCORES_URL);
		// Check the title of the page
		LOGGER.info("Page title is: " + driver.getTitle());
		scrapeScores();

		if (scoreData != null && !scoreData.isEmpty()) {
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
		driver.findElement(By.linkText("calcio")).click();
		driver.findElement(By.linkText("OGGI")).click();

		// driver.findElement(By.cssSelector("#CALCIO_0 > div:nth-child(1) >
		// div:nth-child(1) > a:nth-child(1)")).click();
		// driver.findElement(By.xpath("id('CALCIO_0')/x:div/x:div[1]/x:a[1]")).click();
		WebElement tableElement = driver.findElement(By.xpath(".//table"));

		// create empty table object and iterate through all rows of the found
		// table element
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
			Double home = new Double(userTable.get(i).get("1").getText());
			Double draw = new Double(userTable.get(i).get("X").getText());
			Double away = new Double(userTable.get(i).get("2").getText());
			Double under = new Double(userTable.get(i).get("UNDER").getText());
			Double over = new Double(userTable.get(i).get("OVER").getText());
			Double gol = new Double(userTable.get(i).get("GOL").getText());
			Double noGol = new Double(userTable.get(i).get("NOGOL").getText());

			// Populating Oddss Map to write in data model (e.g excel)
			data.put(i + start, new Odds(match, home, draw, away, under, over, gol, noGol));
		}
		LOGGER.info("Done");
	}

	private void populateOddssSheet() throws FileNotFoundException, IOException {
		// private void populateExcel() {
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_NAME));
		// int index = workbook.getSheetIndex("Quote");
		// workbook.removeSheetAt(index);

		XSSFSheet sheet = workbook.getSheet(OddsS_SHEET);
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
		driver.findElement(By.linkText("FINALI")).click();
		List<WebElement> livescoreTables = driver.findElements(By.className("table-livescore"));

		List<String> fields = new ArrayList<String>();
		fields.add("space1");
		fields.add("time");
		fields.add("space2");
		fields.add("home");
		fields.add("score");
		fields.add("away");
		fields.add("live");
		fields.add("space3");
		fields.add("space4");

		// create empty table object and iterate through all rows of the found
		// table element
		ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();

		if (!livescoreTables.isEmpty()) {

			for (WebElement tableElement : livescoreTables) {
				WebElement tbody = tableElement.findElement(By.xpath(".//tbody"));

				List<WebElement> rowElements = tbody.findElements(By.xpath(".//tr"));

				// iterate through all rows and add their content to table array
				for (WebElement rowElement : rowElements) {
					HashMap<String, WebElement> row = new HashMap<String, WebElement>();

					// add table cells to current row
					int columnIndex = 0;
					List<WebElement> cellElements = rowElement.findElements(By.xpath(".//td"));
					for (WebElement cellElement : cellElements) {
						System.out.println("--------->" + fields.get(columnIndex) + " : " + cellElement.getText());
						row.put(fields.get(columnIndex), cellElement);
						columnIndex++;
					}
					userTable.add(row);
				}
			}

		}

		for (int i = 1; i < userTable.size(); i++) {
			String time = userTable.get(i).get("time").getText();
			Team home = new Team(userTable.get(i).get("home").getText());
			Team away = new Team(userTable.get(i).get("away").getText());
			String score = userTable.get(i).get("score").getText();
			String live = userTable.get(i).get("live").getText();
			Integer goalsHomeTeam = 0;
			Integer goalsAwayTeam = 0;
			if (score != null && !"-".equals(score)) {
				goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf(":")));
				goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf(":") + 1, score.length()));
			}
			LOGGER.debug(home.getName());

			// Populating Oddss Map to write in data model (e.g excel)
			scoreData.put(i + start, new Result(new Date(), home, away, goalsHomeTeam, goalsAwayTeam));
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
		Set<Integer> keyset = data.keySet();
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
			LOGGER.info("Oddss have been writed in: " + FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("Done");
	}

	private void populateLeagues() {
		// Creating Leagues
		League serieA = new League("Serie A", "http://www.direttagoal.it/odds");
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
