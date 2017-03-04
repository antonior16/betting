package local.projects.betting.data.entry.api.football.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

@JsonIgnoreProperties(
    ignoreUnknown = true)
public class Fixture implements Serializable {
  private Team homeTeamName;
  private Team awayTeamName;
  private Result result;
  private String status;
  private Odds odds;
  private Date date;
  
  public Fixture() {
    
  }
  
  public Fixture(Date date,Team homeTeamName, Team awayTeamName, String score, Result result, Odds odds) {
    super();
    this.date = date;    
    this.homeTeamName = homeTeamName;
    this.awayTeamName = awayTeamName;
    this.result = result;
    this.odds = odds;
  }
  
  /**
   * @return the homeTeamName
   */
  public Team getHomeTeamName() {
    return homeTeamName;
  }
  
  /**
   * @param homeTeamName
   *          the homeTeamName to set
   */
  public void setHomeTeamName(Team homeTeamName) {
    this.homeTeamName = homeTeamName;
  }
  
  /**
   * @return the awayTeamName
   */
  public Team getAwayTeamName() {
    return awayTeamName;
  }
  
  /**
   * @param awayTeamName
   *          the awayTeamName to set
   */
  public void setAwayTeamName(Team awayTeamName) {
    this.awayTeamName = awayTeamName;
  }
  
  /**
   * @return the result
   */
  public Result getResult() {
    return result;
  }
  
  /**
   * @param result
   *          the result to set
   */
  public void setResult(Result result) {
    this.result = result;
  }
  
  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }
  
  /**
   * @param date
   *          the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }
  
  /**
   * @return the odd
   */
  public Odds getOdds() {
    return odds;
  }
  
  /**
   * @param odd
   *          the odd to set
   */
  public void setOdd(Odds odds) {
    this.odds = odds;
  }
  
  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }
  
  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }
  
  /**
   * @param odds
   *          the odds to set
   */
  public void setOdds(Odds odds) {
    this.odds = odds;
  }
  
  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((awayTeamName == null) ? 0 : awayTeamName.hashCode());
    result = prime * result + ((homeTeamName == null) ? 0 : homeTeamName.hashCode());
    result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
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
    Fixture other = (Fixture) obj;
    if (awayTeamName == null) {
      if (other.awayTeamName != null)
        return false;
    } else if (!awayTeamName.equals(other.awayTeamName))
      return false;
    if (homeTeamName == null) {
      if (other.homeTeamName != null)
        return false;
    } else if (!homeTeamName.equals(other.homeTeamName))
      return false;
    if (result == null) {
      if (other.result != null)
        return false;
    } else if (!result.equals(other.result))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    return true;
  }
  
  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Score [homeTeamName=" + homeTeamName + ", awayTeamName=" + awayTeamName + ", result=" + result
        + ", date=" + date + "]";
  }
}
