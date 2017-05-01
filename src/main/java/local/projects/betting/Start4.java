package local.projects.betting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.dao.LeagueDao;
import local.projects.betting.dao.OddsDao;

@Component
public class Start4 {
	@Autowired
	public LeagueDao leagueDao;
	
	@Autowired
	public OddsDao oddsDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Start4.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		Start4 p = context.getBean(Start4.class);
		p.oddsDao.clearMatch();
	}
}
