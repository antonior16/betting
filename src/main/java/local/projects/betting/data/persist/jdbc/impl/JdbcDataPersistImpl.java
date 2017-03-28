package local.projects.betting.data.persist.jdbc.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import local.projects.betting.api.DataPersist;
import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

@Component
public class JdbcDataPersistImpl implements DataPersist {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDataPersistImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void persistOdds(Map<Integer, Odds> odds) {
		if (!odds.isEmpty()) {
			jdbcTemplate.update("DELETE FROM quote");

			Set<Integer> keyset = odds.keySet();
			for (Integer key : keyset) {
				final Odds objArr = odds.get(key);

				// Saving only match having Odds

				LOGGER.debug(objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
				String SQL = "insert into quote (DataPartita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
				LOGGER.info(objArr.toString());
				jdbcTemplate.update(SQL, objArr.getOddsDate(), objArr.getHomeTeamName().getName(),
						objArr.getAwayTeamName().getName(), objArr.getHomeWin(), objArr.getDraw(), objArr.getAwayWin(),
						objArr.getUnder(), objArr.getOver(), objArr.getGol(), objArr.getNoGol());
				LOGGER.info("Created Record Home = " + objArr.getHomeTeamName() + " Away = " + objArr.getAwayTeamName()
						+ "in quote");

				SQL = "insert into partite (Data_Partita,Casa, Trasferta, S1 , SX , S2, Under, Over, Gol, NoGol) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jdbcTemplate.update(SQL, objArr.getOddsDate(), objArr.getHomeTeamName().getName(),
						objArr.getAwayTeamName().getName(), objArr.getHomeWin(), objArr.getDraw(), objArr.getAwayWin(),
						objArr.getUnder(), objArr.getOver(), objArr.getGol(), objArr.getNoGol());
				LOGGER.info("Created Record Home = " + objArr.getHomeTeamName() + " Away = " + objArr.getAwayTeamName()
						+ "in partite");
			}
		}
		return;
	}

	@Override
	public void persistFixtures(Map<Integer, Fixture> fixtures) {
		// TODO Auto-generated method stub

	}

	@Override
	public void persistResults(Map<Integer, Result> scores) {
		if (!scores.isEmpty()) {
			Set<Integer> keyset = scores.keySet();
			for (Integer key : keyset) {
				final Result objArr = scores.get(key);

				// Saving only match having Odds

				LOGGER.debug(objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
				String resultDate = null;
				if (objArr.getOddsDate() != null) {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					resultDate = df.format(new Date()).toString();
				}
				String SQL = "insert into risultati (data_partita,Casa, Trasferta, Risultato, Segno, gol_nogol, under_over) values (?, ?, ?, ?, ?, ?,?)";
				try {
					LOGGER.info(objArr.toString());
					jdbcTemplate.update(SQL, objArr.getOddsDate(), objArr.getHomeTeamName().getName(),
							objArr.getAwayTeamName().getName(), objArr.getSign(), objArr.getScore(),
							objArr.getGoalNoGol(), objArr.getUnderOver());
					LOGGER.info("Created Record Home = " + objArr.getHomeTeamName() + " Away = "
							+ objArr.getAwayTeamName() + "in risultati");
				} catch (Exception e) {
					LOGGER.error("Error performing " + SQL, e);
				}
			}
		}
		return;
	}
}
