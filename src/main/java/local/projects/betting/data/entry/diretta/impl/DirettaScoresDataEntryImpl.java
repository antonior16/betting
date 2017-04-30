package local.projects.betting.data.entry.diretta.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import local.projects.betting.api.ScoreDataEntry;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.dao.ResultDao;
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

	private Date scoreDate;

	@Autowired
	private LeagueDao leagueDao;

	@Autowired
	public ResultDao resultDao;

	public DirettaScoresDataEntryImpl() {
	}

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

		Map<Integer, Result> results = new HashMap<Integer, Result>();
		List<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
		for (League league : leagueDao.listLeagues()) {
			driver.get(league.getScoresUrl());
			// Check the title of the page
			LOGGER.info("Page title is: " + driver.getTitle());
			userTable.addAll(extractRowFromHtmlTable(league));
			
			if (!userTable.isEmpty()) {
				for (int i = 0; i < userTable.size(); i++) {
					Result result;
					try {
						result = buildResult(userTable.get(i));
						resultDao.create(result);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			LOGGER.info("No Scores found for " + league.getName() + " " + "on "+ league.getScoresUrl());
		}
		return results;
	}

	private Result buildResult(HashMap<String, WebElement> userTable) throws ParseException {
		String score = userTable.get("score").getText();
		Result result = null;
		if (score != null) {
			// if (score != null &&
			// "Finale".equalsIgnoreCase(userTable.get(i).get("timer").getText()))
			// {
			if (score != null) {
				String time = userTable.get("time").getText();
				Team home = new Team(userTable.get("home").getText().trim());
				Team away = new Team(userTable.get("away").getText().trim());
				LOGGER.info("----------->" + home.getName());
				Integer goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf(":") - 1));
				Integer goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf(":") + 2, score.length()));
				// Populating Oddss Map to write in data model (e.g
				// excel)
				Date scoreDate = getResultDate(time);
				result = new Result(scoreDate, home, away, goalsHomeTeam, goalsAwayTeam);
				LOGGER.debug(home.getName() + " - " + away.getName() + " score has been added ");
			}
		}
		return result;
	}

	private Date getResultDate(String time) throws ParseException {
		int day = Integer.parseInt(time.substring(0, 2));
		int month = Integer.parseInt(time.substring(3, 5));

		String stringDate = day + "/" + String.format("%02d", month) + "/" + "2017";
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

		Date scoreDate = format.parse(stringDate);

		return scoreDate;
	}

	private List<HashMap<String, WebElement>> extractRowFromHtmlTable(League league) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
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
					if (resultTime != null) {
						Date resultDate;
						try {
							resultDate = getResultDate(resultTime);
							if (resultDate.after(lastOddsUpdate)) {
								continue;
							} else if (resultDate.before(lastOddsUpdate)) {
								break;
							}

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
						} catch (ParseException e) {
							LOGGER.error("Error parsing result Date ", e);
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

	private long getDiffDays(String timeFrame) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
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
