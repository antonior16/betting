package local.projects.betting.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import local.projects.betting.dao.OddsDao;
import local.projects.betting.model.Odds;

public class OddsDaoJdbcTemplateImpl implements OddsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(OddsDaoJdbcTemplateImpl.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Odds odds) {
		LOGGER.debug("Inserting :" + odds.getHomeTeamName() + " : " + odds.getAwayTeamName().getName());
		String SQL = "insert into quote (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		LOGGER.info(odds.toString());
		int oddsId = jdbcTemplate.update(SQL, odds.getOddsDate(), odds.getHomeTeamName().getName(),
				odds.getAwayTeamName().getName(), odds.getHomeWin(), odds.getDraw(), odds.getAwayWin(), odds.getUnder(),
				odds.getOver(), odds.getGol(), odds.getNoGol());

		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + odds.getHomeTeamName() + " Away = "
				+ odds.getAwayTeamName() + "in quote");

		SQL = "insert into partite (Data_Partita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(SQL, odds.getOddsDate(), odds.getHomeTeamName().getName(), odds.getAwayTeamName().getName(),
				odds.getHomeWin(), odds.getDraw(), odds.getAwayWin(), odds.getUnder(), odds.getOver(), odds.getGol(),
				odds.getNoGol());
		LOGGER.info("Created Record " + oddsId + "for: " + "Home = " + odds.getHomeTeamName() + " Away = "
				+ odds.getAwayTeamName() + "in partite");
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

}
