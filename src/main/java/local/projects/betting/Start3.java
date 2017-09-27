package local.projects.betting;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.api.data.extract.LeagueDataExtract;
import local.projects.betting.api.data.extract.OddsDataExtract;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.model.League;

@Component
public class Start3 {
	@Resource
	private OddsDataExtract oddsDataExtract;

	@Resource
	private FixtureDataExtract fixturesDataExtract;

	@Autowired
	private LeagueDataExtract LeagueDataExtract;

	@Autowired
	private LeagueDao leagueDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		try {
			Start3 p = context.getBean(Start3.class);
			// p.extractOdds();
			//p.extractFixtures();
			p.extractLeagues();


		} catch (Exception e) {
			LOGGER.error("An Exception has occurred: ", e.getMessage());
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}

	}

	/**
	 * Client for extract matchday odds
	 */
	public void extractOdds() {
		oddsDataExtract.extractOdds(null);
	}

	public void extractFixtures() {
		for (League league : leagueDao.listLeagues4Results()) {
			Date lastResultsUpdate = league.getLastResultsUpdate();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fixtureDate = simpleDateFormat.format(lastResultsUpdate);

			fixturesDataExtract.extractResults(league, fixtureDate);
		}
	}

	public void extractLeagues() {

			LeagueDataExtract.getNextOddsDate(new Long(1));
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

	public LeagueDao getLeagueDao() {
		return leagueDao;
	}

	public void setLeagueDao(LeagueDao leagueDao) {
		this.leagueDao = leagueDao;
	}

}
