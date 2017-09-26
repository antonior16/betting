package local.projects.betting.data.extract.snai.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.data.extract.OddsDataExtract;
import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.data.extract.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.extract.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.Result;

public abstract class AbstractSnaiDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl
		implements OddsDataExtract {
	public AbstractSnaiDataEntryImpl() {
	}

	public AbstractSnaiDataEntryImpl(WebDriverEnum webDriver) {
		super(webDriver);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSnaiDataEntryImpl.class);
}
