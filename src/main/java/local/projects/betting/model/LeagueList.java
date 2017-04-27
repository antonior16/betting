package local.projects.betting.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LeagueList{
	private List<League> leagueList = new ArrayList<League>();
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private final String sql = "SELECT Leagues.league_id, Leagues.name, Leagues.last_odds_update, Leagues.last_results_update, leagues_odds_providers.odds_provider_id, leagues_results_providers.results_provider_id FROM (Leagues INNER JOIN leagues_odds_providers ON Leagues.league_id = leagues_odds_providers.league_id) INNER JOIN leagues_results_providers ON Leagues.league_id = leagues_results_providers.league_id;";

	public LeagueList() {
		getLeagueList();
	}

	public List<League> getLeagueList() {
		leagueList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(League.class));
		return leagueList;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
