package local.projects.betting.data.entry.snai.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.OddsDataEntry;
import local.projects.betting.api.ScoreDataEntry;
import local.projects.betting.data.entry.selenium.web.driver.impl.AbstractSeleniumWebDriverDataEntryImpl;
import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

public class AbstractSnaiDataEntryImpl extends AbstractSeleniumWebDriverDataEntryImpl
    implements
    OddsDataEntry,
    ScoreDataEntry {
  public AbstractSnaiDataEntryImpl(WebDriverEnum webDriver) {
    super(webDriver);
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSnaiDataEntryImpl.class);
  
  @Override
  public Map<Integer, Result> extractResults(String timeFrame) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Map<Integer, Odds> extractOdds() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
