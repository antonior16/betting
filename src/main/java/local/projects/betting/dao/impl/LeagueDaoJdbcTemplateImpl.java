package local.projects.betting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import local.projects.betting.dao.LeagueDao;
import local.projects.betting.model.League;

public class LeagueDaoJdbcTemplateImpl implements LeagueDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(LeagueDaoJdbcTemplateImpl.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(League league) {
		// TODO Auto-generated method stub

	}

	@Override
	public League getLeague(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<League> listLeagues() {
		String sql = "SELECT leagues_odds_providers.league_id, Leagues.name, odds_providers.url AS odds_url, results_providers.url AS results_url, Leagues.last_odds_update, Leagues.last_results_update, Leagues.status FROM results_providers INNER JOIN (odds_providers INNER JOIN ((Leagues INNER JOIN leagues_odds_providers ON Leagues.league_id = leagues_odds_providers.league_id) INNER JOIN leagues_results_providers ON Leagues.league_id = leagues_results_providers.league_id) ON odds_providers.odds_provider_id = leagues_odds_providers.odds_provider_id) ON results_providers.result_provider_id = leagues_results_providers.results_provider_id WHERE (((Leagues.status)=True));";

		return jdbcTemplate.query(sql, new RowMapper<League>() {
			@Override
			public League mapRow(ResultSet rs, int rowNum) throws SQLException {
				League league = new League();
				league.setLeagueId(rs.getLong("league_id"));
				league.setName(rs.getString("name"));
				league.setLastOddsUpdate(rs.getDate("last_odds_update"));
				league.setLastResultsUpdate((rs.getDate("last_results_update")));
				league.setOddsUrl(rs.getString("odds_url"));
				league.setScoresUrl(rs.getString("results_url"));
				return league;
			}
		});
	}

	@Override
	public List<League> listLeagues4Odds() {
		String sql = "SELECT leagues_odds_providers.league_id, Leagues.name, odds_providers.url AS odds_url, results_providers.url AS results_url, Leagues.last_odds_update, Leagues.last_results_update, Leagues.status FROM results_providers INNER JOIN (odds_providers INNER JOIN ((Leagues INNER JOIN leagues_odds_providers ON Leagues.league_id = leagues_odds_providers.league_id) INNER JOIN leagues_results_providers ON Leagues.league_id = leagues_results_providers.league_id) ON odds_providers.odds_provider_id = leagues_odds_providers.odds_provider_id) ON results_providers.result_provider_id = leagues_results_providers.results_provider_id WHERE (((Leagues.last_odds_update) is null) AND ((Leagues.status)=True));";

		return jdbcTemplate.query(sql, new RowMapper<League>() {
			@Override
			public League mapRow(ResultSet rs, int rowNum) throws SQLException {
				League league = new League();
				league.setLeagueId(rs.getLong("league_id"));
				league.setName(rs.getString("name"));
				league.setLastOddsUpdate(rs.getDate("last_odds_update"));
				league.setLastResultsUpdate((rs.getDate("last_results_update")));
				league.setOddsUrl(rs.getString("odds_url"));
				league.setScoresUrl(rs.getString("results_url"));
				return league;
			}
		});
	}

	@Override
	public List<League> listLeagues4Results() {
		String sql = "SELECT leagues_odds_providers.league_id, Leagues.name, odds_providers.url AS odds_url, results_providers.url AS results_url, Leagues.last_odds_update, Leagues.last_results_update, Leagues.status FROM results_providers INNER JOIN (odds_providers INNER JOIN ((Leagues INNER JOIN leagues_odds_providers ON Leagues.league_id = leagues_odds_providers.league_id) INNER JOIN leagues_results_providers ON Leagues.league_id = leagues_results_providers.league_id) ON odds_providers.odds_provider_id = leagues_odds_providers.odds_provider_id) ON results_providers.result_provider_id = leagues_results_providers.results_provider_id WHERE (((Leagues.last_results_update)<Date()) AND ((Leagues.status)=True));";

		return jdbcTemplate.query(sql, new RowMapper<League>() {
			@Override
			public League mapRow(ResultSet rs, int rowNum) throws SQLException {
				League league = new League();
				league.setLeagueId(rs.getLong("league_id"));
				league.setName(rs.getString("name"));
				league.setLastOddsUpdate(rs.getDate("last_odds_update"));
				league.setLastResultsUpdate((rs.getDate("last_results_update")));
				league.setOddsUrl(rs.getString("odds_url"));
				league.setScoresUrl(rs.getString("results_url"));
				return league;
			}
		});
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLastScoreDate(Long leagueId, Date date) {
		String sql = "update Leagues set last_results_update= ? where league_id = ?";
		// define query arguments
		Object[] params = { date, leagueId };
		int[] types = { Types.DATE, Types.BIGINT };
		jdbcTemplate.update(sql, params, types);
	}

	@Override
	public void update(League league) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLastOddsDate(Long leagueId, Date date) {
		String sql = "update Leagues set last_odds_update= ? where league_id = ?";
		// define query arguments
		Object[] params = { date, leagueId };
		int[] types = { Types.DATE, Types.BIGINT };
		jdbcTemplate.update(sql, params, types);
	}
}
