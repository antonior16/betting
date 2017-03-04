package local.projects.betting;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

/**
 * Hello world!
 */
public class Start {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Start.class);
  
  public static void main(String[] args) {
//    String score = "3:2";
//    Integer goalsHomeTeam = Integer.parseInt(score.substring(0, score.indexOf(":")));    
//    Integer goalsAwayTeam = Integer.parseInt(score.substring(score.indexOf(":") + 1, score.length()));
//    
//    System.out.println(goalsHomeTeam);
//    System.out.println(goalsAwayTeam);
//    
    Odds o = new Odds();
    Result r = new Result();
    
    o.setAwayTeamName(new Team("lazio"));
    o.setHomeTeamName(new Team("roma"));
    o.setDate(new Date(2017,02,25));
    
    r.setAwayTeamName(new Team("lazio"));
    r.setHomeTeamName(new Team("roma"));
    r.setDate(new Date(2017,02,25));
    
    System.out.println(o.equals(r));
    

    
  }
}
