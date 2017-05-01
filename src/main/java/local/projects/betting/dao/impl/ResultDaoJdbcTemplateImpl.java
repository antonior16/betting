package local.projects.betting.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import local.projects.betting.dao.ResultDao;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

public class ResultDaoJdbcTemplateImpl implements ResultDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultDaoJdbcTemplateImpl.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Result result) {
		// Saving only match having Odds
		LOGGER.debug(result.getHomeTeamName() + " : " + result.getAwayTeamName().getName());
		String SQL = "insert into risultati (data_partita,Casa, Trasferta, Risultato, Segno, gol_nogol, under_over,multigol_2_4, multigol_2_3) values (?, ?, ?, ?, ?, ?,?,?,?)";
		LOGGER.info(result.toString());
		int resultId = jdbcTemplate.update(SQL, result.getOddsDate(), result.getHomeTeamName().getName(),
				result.getAwayTeamName().getName(), result.getSign(), result.getScore(), result.getGoalNoGol(),
				result.getUnderOver(), result.getIs2To4Multigol(), result.getIs2To3Multigol());
		LOGGER.info("Created Record " + resultId + " Home = " + result.getHomeTeamName() + " Away = "
				+ result.getAwayTeamName());
	}

	@Override
	public Odds getResult(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Result> listResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Integer id, Integer age) {
		// TODO Auto-generated method stub

	}
}
