package local.projects.betting.data.extract.snai.impl;

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
import org.springframework.beans.factory.annotation.Autowired;

import local.projects.betting.BettingUtil;
import local.projects.betting.api.data.extract.OddsDataExtract;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.dao.OddsDao;
import local.projects.betting.data.extract.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.extract.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;
import local.projects.betting.model.Odds;

public class SnaiOddsDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl implements OddsDataExtract {
	private static final Logger LOGGER = LoggerFactory.getLogger(SnaiOddsDataEntryImpl.class);
	@Autowired
	public OddsDao oddsDao;

	@Autowired
	private LeagueDao leagueDao;

	public SnaiOddsDataEntryImpl() {
	}

	public SnaiOddsDataEntryImpl(WebDriverEnum webDriver) {
		super(webDriver);
	}

	public Map<Integer, Fixture> extractOdds() {
		Map<Integer, Fixture> result = new HashMap<Integer, Fixture>();
		try {
			oddsDao.clearMatch();
			String todayString = getTodayDate();

			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
			Date todayDate = sf.parse(todayString + "/" + BettingUtil.getYear());

			List<League> leaguesList = leagueDao.listLeagues();
			for (League league : leaguesList) {
				if (todayDate.equals(league.getLastOddsUpdate())) {
					continue;
				} else {
					driver.get(league.getOddsUrl());
					// And now use this to visit Google
					LOGGER.info("----------> LEAGUE : " + league.getName());
					// Check the title of the page
					LOGGER.info("---------> Page title is: " + driver.getTitle());

					WebDriverWait wait = new WebDriverWait(driver, 120);

					String dateOdds = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("h4"))).getText();

					// Check for available odds
					if (dateOdds != null) {
						Date nextOddsDate;
						if (dateOdds.trim().equals(todayString)) {
							LOGGER.info("--------> Today matches for: " + league.getName());
							nextOddsDate = new Date();
							List<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
							userTable.addAll(extractRowFromTable());

							if (!userTable.isEmpty()) {
								// Splitting Odds
								for (int i = 0; i < userTable.size(); i++) {
									Fixture odds = buildFixture(userTable.get(i), league);
									// Populating Odds Map to write in data
									result.put(i, odds);
									oddsDao.save(odds);
									LOGGER.info("-------> Saved odds: " + odds + "-" + odds.getAwayTeamName());
								}
							}
						} else {
							LOGGER.info("--------> No matches for: " + league.getName());
							dateOdds += "/" + BettingUtil.getYear();
							// sf = new SimpleDateFormat("dd/MM/yyyy",
							// Locale.ITALIAN);
							try {
								nextOddsDate = sf.parse(dateOdds.trim());
								LOGGER.info("--------> " + league.getName() + ": Next matches on: " + nextOddsDate);
								leagueDao.updateLastOddsDate(league.getLeagueId(), nextOddsDate);
								leagueDao.updateLastScoreDate(league.getLeagueId(), nextOddsDate);
							} catch (ParseException e) {
								LOGGER.error("An exception has occurred " + e);
							}
							continue;
						}
						LOGGER.info(
								"--------> " + league.getName() + ": Next matches date updated to: " + nextOddsDate);
						leagueDao.updateLastOddsDate(league.getLeagueId(), nextOddsDate);
						leagueDao.updateLastScoreDate(league.getLeagueId(), nextOddsDate);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error parsing element ", e);
		} finally {
			driver.quit();
		}
		return result;
	}

	private String getTodayDate() {
		// build today string
		String todayString = String.format("%02d", BettingUtil.getDay()) + "/"
				+ String.format("%02d", BettingUtil.getMonth());
		return todayString;
	}

	private Fixture buildFixture(HashMap<String, WebElement> userTable, League league) {
		Fixture fixture = null;
		Date oddsDate = new Date();
		try {
			String match = userTable.get("1X2 FINALE,U/O ,G/NG").getText();
			// match =
			// match.substring(match.indexOf("\n"),
			// match.length());
			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			Double home = new Double(format.parse(userTable.get("1").getText()).doubleValue());
			Double draw = new Double(format.parse(userTable.get("X").getText()).doubleValue());
			Double away = new Double(format.parse(userTable.get("2").getText()).doubleValue());
			Double under = new Double(format.parse(userTable.get("UNDER").getText()).doubleValue());
			Double over = new Double(format.parse(userTable.get("OVER").getText()).doubleValue());
			Double gol = new Double(format.parse(userTable.get("GOL").getText()).doubleValue());
			Double noGol = new Double(format.parse(userTable.get("NOGOL").getText()).doubleValue());
			String team1 = match.substring(6, match.indexOf("-") - 1).trim();
			String team2 = match.substring(match.indexOf("-") + 1, match.length()).trim();
			Odds odds = new Odds(home, draw, away, under, over, gol, noGol);
			fixture = new Fixture(oddsDate, team1, team2, odds, league);
		} catch (ParseException e) {
			LOGGER.error("Error parsing element ", e);
		}

		return fixture;
	}

	protected List<HashMap<String, WebElement>> extractRowFromTable() throws Exception {
		// wait.until(ExpectedConditions.elementToBeClickable(By.linkText("calcio"))).click();
		// driver.findElement(By.linkText("calcio")).click();
		// driver.findElement(By.linkText("OGGI")).click();
		// wait.until(ExpectedConditions.elementToBeClickable(By.linkText("OGGI"))).click();

		List<HashMap<String, WebElement>> result = new ArrayList<HashMap<String, WebElement>>();
		List<String> tableHeaders = extractTableHeader();
		WebDriverWait wait = new WebDriverWait(driver, 120);

		WebElement tableElement = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("tbody")));
		List<WebElement> rowElements = tableElement.findElements(By.tagName("tr"));
		for (WebElement rowElement : rowElements) {
			HashMap<String, WebElement> row = new HashMap<String, WebElement>();
			// add table cells to current row
			int columnIndex = 0;
			List<WebElement> cellElements = rowElement.findElements(By.tagName("td"));
			for (WebElement cellElement : cellElements) {
				row.put(tableHeaders.get(columnIndex), cellElement);
				columnIndex++;
			}
			result.add(row);
		}
		return result;
	}

	protected List<String> extractTableHeader() throws Exception {
		ArrayList<String> tableHeaders = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, 120);
		WebElement tableElement = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("thead")));
		ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
		List<WebElement> rowElements = tableElement.findElements(By.tagName("tr"));

		// get column names of table from table headers
		List<WebElement> headerElements = rowElements.get(0).findElements(By.tagName("th"));
		for (WebElement headerElement : headerElements) {
			tableHeaders.add(headerElement.getText());
		}
		return tableHeaders;
	}
}
