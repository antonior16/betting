package local.projects.betting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.api.data.extract.OddsDataExtract;
import local.projects.betting.dao.FixtureDao;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.dao.OddsDao;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;

@Component
public class Start3 {
	@Resource
	private OddsDataExtract oddsDataExtract;

	@Resource
	private FixtureDataExtract fixturesDataExtract;

	@Autowired
	private LeagueDao leagueDao;

	@Autowired
	private OddsDao oddsDao;

	@Autowired
	private FixtureDao fixtureDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		try {
			Start3 p = context.getBean(Start3.class);
			p.extractFixtures();
			p.extractOdds();
		} catch (Exception e) {
			LOGGER.error("An Exception has occurred: ", e);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}

	}

	/**
	 * Client for extract matchday odds
	 */
	public void extractOdds() {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		// Clear Odds Table
		oddsDao.clearMatch();

		// Retrieving leagues with match on this day
		List<League> leagues = leagueDao.listLeagues();

		// Extract Odds for found leagues
		for (League league : leagues) {
			fixtures.addAll(oddsDataExtract.extractOdds(league));
		}

		// persist odds
		if (fixtures != null && !fixtures.isEmpty()) {
			persistOdds(fixtures);
		}
	}

	public void extractFixtures() {
		// Extract fixturee
		for (League league : leagueDao.listLeagues4Results()) {
			Date lastResultsUpdate = league.getLastResultsUpdate();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fixtureDate = simpleDateFormat.format(lastResultsUpdate);

			List<Fixture> fixtures = fixturesDataExtract.extractFixtures(league, fixtureDate);

			// Persist fixtures
			for (Fixture fixture : fixtures) {
				fixtureDao.save(fixture);
				LOGGER.info("Saved fixture for: " + fixture.getHomeTeamName() + "-" + fixture.getAwayTeamName()
						+ fixture.getResult().toString());
			}
			// After syncing both Odds and Fixtures set next mathc day
			// Date nextOddsDate = leagueDataExtract.getNextOddsDate(leagueId);
			// leagueDao.updateNextMatchDay(leagueId, nextOddsDate);
		}
	}

	public void persistOdds(List<Fixture> fixtures) {
		for (Fixture fixture : fixtures) {
			oddsDao.save(fixture);
		}
	}

}
