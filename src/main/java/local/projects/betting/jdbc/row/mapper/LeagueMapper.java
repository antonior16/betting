package local.projects.betting.jdbc.row.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import local.projects.betting.model.League;
import local.projects.betting.model.Odds;

public class LeagueMapper implements RowMapper<League> {
	public League mapRow(ResultSet rs, int rowNum) throws SQLException {
		League league = new League();
		league.setName(rs.getString("name"));
		league.setLastOddsUpdate(rs.getDate("last_odds_update"));
		league.setLastResultsUpdate((rs.getDate("last_results_update")));
		league.setOddsUrl(rs.getString("odds_url"));
		league.setScoresUrl(rs.getString("results_url"));
		return league;
	}
}