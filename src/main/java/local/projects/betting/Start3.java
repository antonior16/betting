package local.projects.betting;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.api.DataEntry;
import local.projects.betting.api.DataPersist;
import local.projects.betting.model.Odds;

/**
 * Hello world!
 */
@Component
public class Start3 {
  @Resource(name="odds")
  private DataEntry dataEntry;
  @Resource(name="jdbcDataPersist")
  private DataPersist dataPersist;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);
  
  public static void main(String[] args) {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:application-context.xml");
    
    Start3 p = context.getBean(Start3.class);
    p.extractOdds();
  }
  
  /**
   * @param s
   */
  private void extractOdds() {
    Map<Integer, Odds> extractOdds = dataEntry.extractOdds();
if (extractOdds != null && !extractOdds.isEmpty()) {      
      dataPersist.persistOdds(extractOdds);
    }
  }
  
  // private void extractResults(String timeFrame, DataPersistProviderEnum dataPersistProvider) {
  // DataEntry dataEntry = new DirettaScopou resDataEntryImpl(WebDriverEnum.PHANTOMJS);
  // Map<Integer, Result> scores = dataEntry.extractResults(timeFrame);
  //
  // if (scores != null && !scores.isEmpty()) {
  // DataPersist dataPersist = getDataPersistProvider(dataPersistProvider);
  // dataPersist.persistResults(scores);
  // }
  // }
}
