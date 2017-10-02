/**
 *
 */
package local.projects.betting.data.extract.football.data.impl;

import java.util.HashMap;
import java.util.Map;

import local.projects.betting.model.League;

public class FootballDataLeagueDb {

	private static final Map<Long, League> leagueMap = new HashMap<Long, League>();

	private static FootballDataLeagueDb instance = null;

	protected FootballDataLeagueDb() {
		buildLeagueMap();
	}

	public static FootballDataLeagueDb getInstance() {
		if (instance == null) {
			instance = new FootballDataLeagueDb();
		}
		return instance;
	}

	private void buildLeagueMap() {
		leagueMap.put(new Long(1), new League(new Long(456), "Serie A"));
		leagueMap.put(new Long(2), new League(new Long(459), "Serie B"));
		leagueMap.put(new Long(3), new League(new Long(459), "Liga"));
		leagueMap.put(new Long(4), new League(new Long(455), "Ligue 1"));
		leagueMap.put(new Long(5), new League(new Long(452), "Bundesliga"));
		leagueMap.put(new Long(6), new League(new Long(445), "Premier League"));
	}

	public static Map<Long, League> getLeagueMap() {
		return leagueMap;
	}
}
