package local.projects.betting.data.entry.snai.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.OddsDataEntry;
import local.projects.betting.api.ScoreDataEntry;
import local.projects.betting.data.entry.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.Result;

public class AbstractSnaiDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl
		implements OddsDataEntry, ScoreDataEntry {
	public AbstractSnaiDataEntryImpl() {		
	}

	public AbstractSnaiDataEntryImpl(WebDriverEnum webDriver) {
		super(webDriver);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSnaiDataEntryImpl.class);

	@Override
	public Map<Integer, Fixture> extractResults(String timeFrame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Fixture> extractOdds() {
		// TODO Auto-generated method stub
		return null;
	}

}
