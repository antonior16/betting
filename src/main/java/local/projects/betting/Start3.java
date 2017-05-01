package local.projects.betting;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.api.DataEntry;

/**
 * Hello world!
 */
@Component
public class Start3 {
	@Resource(name = "odds")
	private DataEntry oddsDataEntry;

	@Resource(name = "results")
	private DataEntry resultsDataEntry;

	private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		Start3 p = context.getBean(Start3.class);
//		 p.extractOdds();
		p.extractResults(null);
	}

	/**
	 * @param s
	 */
	private void extractOdds() {
		oddsDataEntry.extractOdds();
	}

	private void extractResults(String timeFrame) {
		resultsDataEntry.extractResults(timeFrame);
	}
}
