/**
 *
 */
package local.projects.betting.data.extract.football.data.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.api.data.extract.LeagueDataExtract;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;
import local.projects.betting.model.json.FixturesCollection;

public class LeagueFootballDataDataExtractImpl implements LeagueDataExtract {

	private static final Logger LOGGER = LoggerFactory.getLogger(LeagueFootballDataDataExtractImpl.class);

	@Resource
	private WebClient footballDataRestClient;

	@Autowired
	private FixtureDataExtract fixtureDataExtract;

	@Autowired
	private LeagueDao leagueDao;

	private Map<Long, League> leagueMap = new HashMap<Long, League>();

	public LeagueFootballDataDataExtractImpl() {
	}

	@Override
	public Date getNextOddsDate(Long leagueId) {
		Date matchDate = null;
		League league = FootballDataLeagueDb.getInstance().getLeagueMap().get(leagueId);
		// Get league info to extract match day

		footballDataRestClient.path("/competitions/" + league.getLeagueId()).accept(MediaType.APPLICATION_JSON);
		league = footballDataRestClient.get(League.class);

		if (league != null) {
			// find match day date
			footballDataRestClient.path("/fixtures");
			footballDataRestClient.query("matchday", league.getCurrentMatchDay());
			FixturesCollection fixturesCollection = footballDataRestClient.get(FixturesCollection.class);

			List<Fixture> fixturesList = fixturesCollection.getFixtures();

			if (fixturesList != null && !fixturesList.isEmpty()) {
				matchDate = fixturesList.get(0).getMatchDate();
			} else {
				LOGGER.info("League not found");
			}
			LOGGER.info("Next match date: " + matchDate);
		}
		return matchDate;
	}
}
