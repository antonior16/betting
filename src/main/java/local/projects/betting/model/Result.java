package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
    ignoreUnknown = true)
public class Result extends Odds implements Serializable {
  private int goalsHomeTeam;
  private int goalsAwayTeam;
  
  public Result() {
  }
  
  public Result(Date date,Team homeTeamName, Team awayTeamName, int goalsHomeTeam, int goalsAwayTeam) {
    this.setDate(date);
    this.setHomeTeamName(homeTeamName);
    this.setAwayTeamName(awayTeamName);
    this.goalsHomeTeam = goalsHomeTeam;
    this.goalsAwayTeam = goalsAwayTeam;
  }
  
  /**
   * @return the goalsHomeTeam
   */
  public int getGoalsHomeTeam() {
    return goalsHomeTeam;
  }
  
  /**
   * @param goalsHomeTeam
   *          the goalsHomeTeam to set
   */
  public void setGoalsHomeTeam(int goalsHomeTeam) {
    this.goalsHomeTeam = goalsHomeTeam;
  }
  
  /**
   * @return the goalsAwayTeam
   */
  public int getGoalsAwayTeam() {
    return goalsAwayTeam;
  }
  
  /**
   * @param goalsAwayTeam
   *          the goalsAwayTeam to set
   */
  public void setGoalsAwayTeam(int goalsAwayTeam) {
    this.goalsAwayTeam = goalsAwayTeam;
  }
  
  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + goalsAwayTeam;
    result = prime * result + goalsHomeTeam;
    return result;
  }
  
  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Result other = (Result) obj;
    if (goalsAwayTeam != other.goalsAwayTeam)
      return false;
    if (goalsHomeTeam != other.goalsHomeTeam)
      return false;
    return true;
  }
  
  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Result [goalsHomeTeam=" + goalsHomeTeam + ", goalsAwayTeam=" + goalsAwayTeam + "]";
  }
}
