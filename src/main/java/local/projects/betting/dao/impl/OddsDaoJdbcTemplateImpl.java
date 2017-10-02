package local.projects.betting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import local.projects.betting.dao.OddsDao;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;

public class OddsDaoJdbcTemplateImpl implements OddsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(OddsDaoJdbcTemplateImpl.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Fixture fixture) {
		LOGGER.debug("Inserting :" + fixture.getHomeTeamName() + " : " + fixture.getAwayTeamName());
		insertOddsInQuote(fixture);
		insertOddsInPartite(fixture);
		insertOddsInOciAggio(fixture);
	}

	private void insertOddsInQuote(Fixture fixture) {
		String tableName = "quote";
		String SQL = "insert into " + tableName
				+ " (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		LOGGER.info(fixture.toString());
		int oddsId = jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName(),
				fixture.getAwayTeamName(), fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(),
				fixture.getOdds().getAwayWin(), fixture.getOdds().getUnder(), fixture.getOdds().getOver(),
				fixture.getOdds().getGol(), fixture.getOdds().getNoGol());

		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in quote");
	}

	private void insertOddsInOciAggio(Fixture fixture) {
		String tableName = "oci_aggio";
		String SQL = "insert into " + tableName
				+ " (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol, aggio) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		LOGGER.info(fixture.toString());

		// Formula: 100/(100/Quota1)+(100/QuotaX)+(100/Quota2)

		Double aggio = 100 / (100 / fixture.getOdds().getHomeWin()) + (100 / fixture.getOdds().getDraw())
				+ (100 / fixture.getOdds().getAwayWin());

		int oddsId = jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName(),
				fixture.getAwayTeamName(), fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(),
				fixture.getOdds().getAwayWin(), fixture.getOdds().getUnder(), fixture.getOdds().getOver(),
				fixture.getOdds().getGol(), fixture.getOdds().getNoGol(), aggio);

		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in quote");
	}

	private void insertOddsInPartite(Fixture fixture) {
		String tableName = "partite";
		String SQL;
		SQL = "insert into " + tableName
				+ " (Data_Partita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int oddsId = jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName(),
				fixture.getAwayTeamName(), fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(),
				fixture.getOdds().getAwayWin(), fixture.getOdds().getUnder(), fixture.getOdds().getOver(),
				fixture.getOdds().getGol(), fixture.getOdds().getNoGol());
		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in partite");
	}

	@Override
	public void clearMatch() {
		String tableName = "quote";
		String sql = "DELETE * FROM " + tableName;
		// Execute deletion
		jdbcTemplate.update(sql);
		LOGGER.info("Quote table has been truncated");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see local.projects.betting.dao.OddsDao#getLeagueByOddsMatchDay()
	 */
	@Override
	public List<League> getLeagueByOddsMatchDay() {
		String sql = "SELECT leagues_odds_providers.league_id, Leagues.name, odds_providers.url AS odds_url, results_providers.url AS results_url, Leagues.last_odds_update, Leagues.last_results_update, Leagues.status FROM results_providers INNER JOIN (odds_providers INNER JOIN ((Leagues INNER JOIN leagues_odds_providers ON Leagues.league_id = leagues_odds_providers.league_id) INNER JOIN leagues_results_providers ON Leagues.league_id = leagues_results_providers.league_id) ON odds_providers.odds_provider_id = leagues_odds_providers.odds_provider_id) ON results_providers.result_provider_id = leagues_results_providers.results_provider_id WHERE (((Leagues.last_odds_update) = DATE()) AND ((Leagues.status)=True));";

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
}
