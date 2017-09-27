/**
 *
 */
package local.projects.betting.data.extract.football.data.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;

import local.projects.betting.api.data.extract.LeagueDataExtract;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.model.League;

public class LeagueFootballDataDataExtractImpl implements LeagueDataExtract {

	@Resource
	private WebClient footballDataRestClient;

	@Autowired
	private LeagueDao leagueDao;

	private Map<String, League> leagueMap = new HashMap<String, League>();

	public LeagueFootballDataDataExtractImpl() {
		buildLeagueMap();
	}

	/**
	 *
	 */
	private void buildLeagueMap() {

	}

	@Override
	public void getNextOddsDate(Long leagueId) {
		footballDataRestClient.path("/competitions").accept(MediaType.APPLICATION_JSON);

		Collection<? extends League> collection = footballDataRestClient.getCollection(League.class);

		System.out.println(collection);
	}
}
