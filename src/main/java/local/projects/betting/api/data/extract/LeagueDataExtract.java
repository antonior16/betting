/**
 *
 */
package local.projects.betting.api.data.extract;

import java.util.Date;

public interface LeagueDataExtract {
	public Date getNextOddsDate(Long leagueId);
}
