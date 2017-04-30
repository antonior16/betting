package local.projects.betting;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.dao.LeagueDao;
import local.projects.betting.dao.OddsDao;
import local.projects.betting.dao.ResultDao;
import local.projects.betting.model.League;

/**
 * Hello world!
 */
@Component
public class Start4 {

	@Autowired
	public OddsDao oddsDao;

	@Autowired
	public ResultDao resultDao;

	@Autowired
	public LeagueDao leagueDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(Start4.class);

	public static void main(String[] args) throws ParseException {
		 ApplicationContext context = new
		 ClassPathXmlApplicationContext("classpath:application-context.xml");
		 Start4 p = context.getBean(Start4.class);
		 League l = new League();
		 l.setLeagueId(new Long(1));
		 p.leagueDao.updateLastScoreDate(new Long(1), null);

	}


}
