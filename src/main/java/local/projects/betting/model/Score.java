package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

public class Score extends Odds implements Serializable {
  private Team home;
  private Team away;
  private Result result;
  private Date scoreDate;
  
  public Score(Team home, Team away, Result result) {
    super();
    this.home = home;
    this.away = away;
    this.result = result;
  }
  
  public Team getHome() {
    return home;
  }
  
  public void setHome(Team home) {
    this.home = home;
  }
  
  public Team getAway() {
    return away;
  }
  
  public void setAway(Team away) {
    this.away = away;
  }
  
  public Date getScoreDate() {
    return scoreDate;
  }
  
  public void setScoreDate(Date scoreDate) {
    this.scoreDate = scoreDate;
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
  
  @Override
  public String toString() {
    return "Score [home=" + home + ", away=" + away + ", scoreDate=" + scoreDate + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((away == null) ? 0 : away.hashCode());
    result = prime * result + ((home == null) ? 0 : home.hashCode());
    result = prime * result + ((scoreDate == null) ? 0 : scoreDate.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Score other = (Score) obj;
    if (away == null) {
      if (other.away != null)
        return false;
    } else if (!away.equals(other.away))
      return false;
    if (home == null) {
      if (other.home != null)
        return false;
    } else if (!home.equals(other.home))
      return false;
    if (scoreDate == null) {
      if (other.scoreDate != null)
        return false;
    } else if (!scoreDate.equals(other.scoreDate))
      return false;
    return true;
  }
}
