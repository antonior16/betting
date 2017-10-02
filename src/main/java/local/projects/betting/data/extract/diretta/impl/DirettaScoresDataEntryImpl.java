package local.projects.betting.data.extract.diretta.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.dao.FixtureDao;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.data.extract.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.extract.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;
import local.projects.betting.model.Result;

/**
 * Hello world!
 */
public class DirettaScoresDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl implements FixtureDataExtract {

	private Date scoreDate;

	@Autowired
	private LeagueDao leagueDao;

	@Autowired
	public FixtureDao resultDao;

	public DirettaScoresDataEntryImpl() {
	}

	public DirettaScoresDataEntryImpl(WebDriverEnum webDriver) {
		super(webDriver);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(DirettaScoresDataEntryImpl.class);

	// Scores datasource
	private static final String SCORES_URL = "http://www.diretta.it";

	@Override
	public List<Fixture> extractFixtures(League league, String timeFrame) {

		List<Fixture> results = new ArrayList<Fixture>();
		List<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
		try {
			driver.get(league.getScoresUrl());
			// Check the title of the page
			LOGGER.info("Page title is: " + driver.getTitle());
			userTable.addAll(extractRowFromHtmlTable(league));
			if (!userTable.isEmpty()) {
				for (int i = 0; i < userTable.size(); i++) {
					Fixture fixture;
					try {
						fixture = buildFixture(userTable.get(i), league);
						results.add(fixture);
					} catch (ParseException e) {
						LOGGER.error("An exception has occurred " + e.getMessage());
					}
				}
			}
			LOGGER.info("No Scores found for " + league.getName() + " " + "on " + league.getScoresUrl());

		} catch (Exception e) {
			LOGGER.error("Error parsing element ", e);
		}
		return results;
	}

	private Fixture buildFixture(HashMap<String, WebElement> userTable, League league) throws ParseException {
		String score = userTable.get("score").getText();
		Fixture fixture = null;
		if (score != null) {
			// if (score != null &&
			// "Finale".equalsIgnoreCase(userTable.get(i).get("timer").getText()))
			// {
			if (score != null) {
				String time = userTable.get("time").getText();
				String home = userTable.get("home").getText().trim();
				String away = userTable.get("away").getText().trim();
				LOGGER.info("----------->" + home);
				Integer goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf(":") - 1));
				Integer goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf(":") + 2, score.length()));
				// Populating Oddss Map to write in data model (e.g
				// excel)
				Date scoreDate = getResultDate(time);

				Result result = new Result(goalsHomeTeam, goalsAwayTeam);
				fixture = new Fixture(scoreDate, home, away, result, league);
				LOGGER.debug(home + " - " + away + " score has been added ");
			}
		}
		return fixture;
	}

	private Date getResultDate(String time) throws ParseException {
		int day = Integer.parseInt(time.substring(0, 2));
		int month = Integer.parseInt(time.substring(3, 5));

		String stringDate = day + "/" + String.format("%02d", month) + "/" + "2017";
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

		Date scoreDate = format.parse(stringDate);

		return scoreDate;
	}

	private List<HashMap<String, WebElement>> extractRowFromHtmlTable(League league) throws Exception {
		Date lastOddsUpdate = league.getLastOddsUpdate();
		/*
		 * Search for previous days
		 * driver.findElement(By.className("yesterday")).click(); for (int i =
		 * 0; i < getDiffDays(timeFrame); i++) {
		 * wait.until(ExpectedConditions.elementToBeClickable(By.className(
		 * "yesterday"))).click();
		 * driver.findElement(By.cssSelector("yesterday")).click(); }
		 * wait.until(ExpectedConditions.elementToBeClickable(By.linkText(
		 * "Conclusi"))).click();
		 * driver.findElement(By.linkText("Conclusi")).click();
		 */

		List<String> fields = extractHeaderFromHtmlTable();
		List<HashMap<String, WebElement>> result = new ArrayList<HashMap<String, WebElement>>();
		List<WebElement> livescoreTables = driver.findElements(By.tagName("table"));
		// create empty table object and iterate through all rows of the
		// found
		// table element
		if (!livescoreTables.isEmpty()) {
			for (WebElement tableElement : livescoreTables) {
				WebElement tbody = tableElement.findElement(By.tagName("tbody"));
				List<WebElement> rowElements = tbody.findElements(By.tagName("tr"));
				int rowId = 0;
				for (WebElement rowElement : rowElements) {
					rowId++;
					if (rowId == 1) {
						continue;
					}

					if ("event_round".equalsIgnoreCase(rowElement.getAttribute("class"))) {
						break;
					}

					String resultTime = rowElement.findElement(By.className("time")).getText();
					if (lastOddsUpdate != null && resultTime != null) {
						try {
							Date resultDate = getResultDate(resultTime);
							if (resultDate.compareTo(lastOddsUpdate) == 0) {
								// add table cells to current row
								int columnIndex = 0;
								List<WebElement> cellElements = rowElement.findElements(By.tagName("td"));
								HashMap<String, WebElement> row = new HashMap<String, WebElement>();
								for (WebElement cellElement : cellElements) {
									System.out.println(
											"--------->" + fields.get(columnIndex) + " : " + cellElement.getText());
									row.put(fields.get(columnIndex), cellElement);
									columnIndex++;
								}
								result.add(row);
							} else {
								break;
							}
						} catch (Exception e) {
							LOGGER.error("An Exception has occured", e);
							throw e;
						}
					}
				}
			}
		}
		return result;
	}

	private List<String> extractHeaderFromHtmlTable() {
		List<String> fields = new ArrayList<String>();
		fields.add("time");
		// fields.add("timer");
		fields.add("home");
		fields.add("away");
		fields.add("score");
		// fields.add("top");
		fields.add("space1");
		fields.add("space2");
		return fields;
	}
}
