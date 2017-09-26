package local.projects.betting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import local.projects.betting.dao.OddsDao;
import local.projects.betting.model.Fixture;

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
		String SQL = "insert into quote (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		LOGGER.info(fixture.toString());
		int oddsId = jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName(),
				fixture.getAwayTeamName(), fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(),
				fixture.getOdds().getAwayWin(), fixture.getOdds().getUnder(), fixture.getOdds().getOver(),
				fixture.getOdds().getGol(), fixture.getOdds().getNoGol());

		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in quote");

		SQL = "insert into partite (Data_Partita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName(), fixture.getAwayTeamName(),
				fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(), fixture.getOdds().getAwayWin(),
				fixture.getOdds().getUnder(), fixture.getOdds().getOver(), fixture.getOdds().getGol(),
				fixture.getOdds().getNoGol());
		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in partite");
	}

	@Override
	public void clearMatch() {
		String sql = "DELETE * FROM quote";
		// Execute deletion
		jdbcTemplate.update(sql);
		LOGGER.info("Quote table has been truncated");
	}

}
