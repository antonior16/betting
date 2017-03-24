package local.projects.betting;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.DataEntry;
import local.projects.betting.api.DataPersist;
import local.projects.betting.data.entry.api.football.impl.ApiFootballDataEntryImpl;
import local.projects.betting.data.entry.api.football.model.DataPersistProviderEnum;
import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.data.entry.diretta.impl.DirettaScoresDataEntryImpl;
import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.data.entry.snai.impl.SnaiOddsDataEntryImpl;
import local.projects.betting.data.persist.excel.impl.ExcelDataPersistImpl;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

/**
 * Hello world!
 */
public class Start2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(Start2.class);
	// private String excelPath = "C:/git/betting/Scommesse.xlsx";
	private String excelPath = "Scommesse.xlsx";

	private String excelSheetName;

	public Start2() {
	}

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	/**
	 * @return the sheetName
	 */
	public String getExcelSheetName() {
		return excelSheetName;
	}

	/**
	 * @param sheetName
	 *            the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.excelSheetName = sheetName;
	}

	public static void main(String[] args) {
		Start2 s = new Start2();
		s.setSheetName("Risultati");
		s.extractResults("p1", DataPersistProviderEnum.EXCEL);

//		s.setSheetName("Quote");
//		s.extractOdds(DataPersistProviderEnum.EXCEL);
		//
		// s.setSheetName("Fixtures");
		// s.extractFixtures("p1", DataPersistProviderEnum.EXCEL);
	}

	private void extractFixtures(String timeFrame, DataPersistProviderEnum dataPersistProvider) {
		// DataEntry dataEntry = new ApiFootballDataEntryImpl();
		Map<Integer, Fixture> scores = new ApiFootballDataEntryImpl().extractFixtures(timeFrame);

		if (scores != null && !scores.isEmpty()) {
			DataPersist dataPersist = getDataPersistProvider(dataPersistProvider);
			dataPersist.persistFixtures(scores);
		}
	}

	/**
	 * @param s
	 */
	private void extractOdds(DataPersistProviderEnum dataPersistProvider) {
		DataEntry dataEntry = new SnaiOddsDataEntryImpl(WebDriverEnum.PHANTOMJS);
		Map<Integer, Odds> extractOdds = dataEntry.extractOdds();
		if (extractOdds != null && !extractOdds.isEmpty()) {
			DataPersist dataPersist = getDataPersistProvider(dataPersistProvider);
			dataPersist.persistOdds(extractOdds);
		}
	}

	private void extractResults(String timeFrame, DataPersistProviderEnum dataPersistProvider) {
		DataEntry dataEntry = new DirettaScoresDataEntryImpl(WebDriverEnum.PHANTOMJS);
		Map<Integer, Result> scores = dataEntry.extractResults(timeFrame);

		if (scores != null && !scores.isEmpty()) {
			DataPersist dataPersist = getDataPersistProvider(dataPersistProvider);
			dataPersist.persistResults(scores);
		}
	}

	/**
	 * @return
	 */
	private DataPersist getDataPersistProvider(DataPersistProviderEnum dataPersistProvider) {
		DataPersist dataPersist = null;
		switch (dataPersistProvider) {
		case EXCEL:
			dataPersist = new ExcelDataPersistImpl(getExcelPath(), getExcelSheetName());
			break;
		}
		return dataPersist;
	}
}
