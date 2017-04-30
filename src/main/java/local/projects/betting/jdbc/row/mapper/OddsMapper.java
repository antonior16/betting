package local.projects.betting.jdbc.row.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import local.projects.betting.model.Odds;
import local.projects.betting.model.Team;

public class OddsMapper implements RowMapper<Odds> {
	public Odds mapRow(ResultSet rs, int rowNum) throws SQLException {
		Odds odds = new Odds();
		odds.setOddsDate(rs.getDate("Data_Partita"));
		odds.setHomeTeamName(new Team(rs.getString("Casa")));
		odds.setAwayTeamName(new Team(rs.getString("Trasferta")));
		return odds;
	}
}