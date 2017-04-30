package local.projects.betting.dao.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import local.projects.betting.dao.FixturesDao;
import local.projects.betting.model.League;

public class FixturesDaoJdbcTemplateImpl implements FixturesDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<League, Date> getFixturesWithoutResults() {
		String sql = "SELECT partite_risultati.Data_Partita, partite_risultati.Casa, partite_risultati.Trasferta, partite_risultati.name, partite_risultati.url, partite_risultati.campionato FROM partite_risultati LEFT JOIN risultati ON (partite_risultati.Data_Partita = risultati.data_partita) AND (partite_risultati.Casa = risultati.Casa) WHERE (((risultati.Casa) Is Null)) GROUP BY partite_risultati.Data_Partita, partite_risultati.Casa, partite_risultati.Trasferta, partite_risultati.name, partite_risultati.url, partite_risultati.campionato ORDER BY partite_risultati.campionato;";

//		return jdbcTemplate.query(sql, new RowMapper<Odds>() {
//			@Override
//			public Odds mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Odds odds = new Odds();
//				odds.setOddsDate(rs.getDate("Data_Partita"));
//				odds.setHomeTeamName(new Team(rs.getString("Casa")));
//				odds.setAwayTeamName(new Team(rs.getString("Trasferta")));
//				return odds;
//			}
//		});		
		return null;
	}



}
