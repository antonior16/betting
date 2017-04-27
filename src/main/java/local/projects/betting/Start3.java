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
import local.projects.betting.model.Result;

/**
 * Hello world!
 */
@Component
public class Start3 {
  @Resource(
      name = "odds")
  private DataEntry oddsDataEntry;
  
  @Resource(
      name = "jdbcDataPersist")
  private DataPersist dataPersist;
  
  @Resource(
      name = "results")
  private DataEntry resultsDataEntry;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);
  
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
    Start3 p = context.getBean(Start3.class);
    p.extractOdds();
    // p.extractResults(null);
  }
  
  /**
   * @param s
   */
  private void extractOdds() {
    Map<Integer, Odds> extractOdds = oddsDataEntry.extractOdds();
    if (extractOdds != null && !extractOdds.isEmpty()) {
      dataPersist.persistOdds(extractOdds);
    }
  }
  
  private void extractResults(String timeFrame) {
    Map<Integer, Result> scores = resultsDataEntry.extractResults(timeFrame);
    
    // Map<Integer, Result> scores = new HashMap<Integer, Result>();
    // Result result = new Result(new Date(), new Team("Milan"), new
    // Team("Juventus"), 2, 1);
    // scores.put(1, result);
    if (scores != null && !scores.isEmpty()) {
      dataPersist.persistResults(scores);
    }
  }
}
