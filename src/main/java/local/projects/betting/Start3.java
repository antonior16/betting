package local.projects.betting;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.api.data.extract.OddsDataExtract;

@Component
public class Start3 {
	@Resource(name = "odds")
	private OddsDataExtract oddsDataExtract;

	@Resource(name = "results")
	private FixtureDataExtract fixturesDataExtract;

	private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		Start3 p = context.getBean(Start3.class);
		p.fixturesDataExtract.extractResults("2017-09-24");

	}

	public OddsDataExtract getOddsDataExtract() {
		return oddsDataExtract;
	}

	public void setOddsDataExtract(OddsDataExtract oddsDataExtract) {
		this.oddsDataExtract = oddsDataExtract;
	}

	public FixtureDataExtract getFixturesDataExtract() {
		return fixturesDataExtract;
	}

	public void setFixturesDataExtract(FixtureDataExtract fixturesDataExtract) {
		this.fixturesDataExtract = fixturesDataExtract;
	}

}
