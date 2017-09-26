package local.projects.betting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import local.projects.betting.dao.FixturesDao;
import local.projects.betting.model.Fixture;

public class FixturesDaoJdbcTemplateImpl implements FixturesDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(FixturesDaoJdbcTemplateImpl.class);

	@Override
	public void save(Fixture fixture) {
		// Saving only match having Odds
		LOGGER.debug(fixture.getHomeTeamName() + " : " + fixture.getAwayTeamName());
		String SQL = "insert into risultati (data_partita,Casa, Trasferta, Risultato, Segno, gol_nogol, under_over,multigol_2_4, multigol_2_3) values (?, ?, ?, ?, ?, ?,?,?,?)";
		LOGGER.info(fixture.toString());
		int resultId = jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName(),
				fixture.getAwayTeamName(), fixture.getResult().getSign(), fixture.getResult().getScore(),
				fixture.getResult().getGoalNoGol(), fixture.getResult().getUnderOver(),
				fixture.getResult().getIs2To4Multigol(), fixture.getResult().getIs2To3Multigol());
		LOGGER.info("Created Record " + resultId + " Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName());
	}
}
