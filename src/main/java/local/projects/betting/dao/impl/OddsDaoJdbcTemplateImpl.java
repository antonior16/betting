package local.projects.betting.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import local.projects.betting.dao.OddsDao;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.Odds;

public class OddsDaoJdbcTemplateImpl implements OddsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(OddsDaoJdbcTemplateImpl.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Fixture fixture) {
		LOGGER.debug("Inserting :" + fixture.getHomeTeamName() + " : " + fixture.getAwayTeamName().getName());
		String SQL = "insert into quote (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		LOGGER.info(fixture.toString());
		int oddsId = jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName().getName(),
				fixture.getAwayTeamName().getName(), fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(),
				fixture.getOdds().getAwayWin(), fixture.getOdds().getUnder(), fixture.getOdds().getOver(),
				fixture.getOdds().getGol(), fixture.getOdds().getNoGol());

		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in quote");

		SQL = "insert into partite (Data_Partita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(SQL, fixture.getMatchDate(), fixture.getHomeTeamName().getName(),
				fixture.getAwayTeamName().getName(), fixture.getOdds().getHomeWin(), fixture.getOdds().getDraw(),
				fixture.getOdds().getAwayWin(), fixture.getOdds().getUnder(), fixture.getOdds().getOver(),
				fixture.getOdds().getGol(), fixture.getOdds().getNoGol());
		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + fixture.getHomeTeamName() + " Away = "
				+ fixture.getAwayTeamName() + "in partite");
	}

	@Override
	public Odds getOdds(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Odds> listOdds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Integer id, Integer age) {
	}

	@Override
	public void clearMatch() {
		String sql = "DELETE * FROM quote";
		// Execute deletion
		jdbcTemplate.update(sql);
		LOGGER.info("Quote table has been truncated");
	}

}
