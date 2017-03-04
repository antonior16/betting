package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
    ignoreUnknown = true)
public class Odds implements Serializable {
  private String match;
  private Team homeTeamName;
  private Team awayTeamName;
  private Date date;
  private Double homeWin;
  private Double draw;
  private Double awayWin;
  private Double under;
  private Double over;
  private Double gol;
  private Double noGol;
  private static final long serialVersionUID = -6036961121582000654L;
  
  public Odds() {
    
  }
  
  public Odds(Date date, Team homeTeamName, Team awayTeamName, Double homeWin, Double draw, Double awayWin) {
    this.date = date;
    this.homeTeamName = homeTeamName;
    this.awayTeamName = awayTeamName;
    this.homeWin = homeWin;
    this.draw = draw;
    this.awayWin = awayWin;
  }
  
  public Odds(
      String match,
      Double homeWin,
      Double draw,
      Double awayWin,
      Double under,
      Double over,
      Double gol,
      Double noGol) {
    super();
    this.match = match;
    this.homeWin = homeWin;
    this.draw = draw;
    this.awayWin = awayWin;
    this.under = under;
    this.over = over;
    this.gol = gol;
    this.noGol = noGol;
  }
  
  public Team getHomeTeamName() {
    return homeTeamName;
  }
  
  public void setHomeTeamName(Team homeTeamName) {
    this.homeTeamName = homeTeamName;
  }
  
  public Team getAwayTeamName() {
    return awayTeamName;
  }
  
  public void setAwayTeamName(Team awayTeamName) {
    this.awayTeamName = awayTeamName;
  }
  
  public Date getDate() {
    return date;
  }
  
  public void setDate(Date date) {
    this.date = date;
  }
  
  public Double getHomeWin() {
    return homeWin;
  }
  
  public void setHomeWin(Double homeWin) {
    this.homeWin = homeWin;
  }
  
  public Double getDraw() {
    return draw;
  }
  
  public void setDraw(Double draw) {
    this.draw = draw;
  }
  
  public Double getAwayWin() {
    return awayWin;
  }
  
  public void setAwayWin(Double awayWin) {
    this.awayWin = awayWin;
  }
  
  public Double getUnder() {
    return under;
  }
  
  public void setUnder(Double under) {
    this.under = under;
  }
  
  public Double getOver() {
    return over;
  }
  
  public void setOver(Double over) {
    this.over = over;
  }
  
  public Double getGol() {
    return gol;
  }
  
  public void setGol(Double gol) {
    this.gol = gol;
  }
  
  public Double getNoGol() {
    return noGol;
  }
  
  public void setNoGol(Double noGol) {
    this.noGol = noGol;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((awayTeamName == null) ? 0 : awayTeamName.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((homeTeamName == null) ? 0 : homeTeamName.hashCode());
    return result;
  }
  
 
  
  /* (non-Javadoc)
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
    Odds other = (Odds) obj;
    if (awayTeamName == null) {
      if (other.awayTeamName != null)
        return false;
    } else if (!awayTeamName.equals(other.awayTeamName))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (homeTeamName == null) {
      if (other.homeTeamName != null)
        return false;
    } else if (!homeTeamName.equals(other.homeTeamName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Odds [homeTeamName=" + homeTeamName + ", awayTeamName=" + awayTeamName + ", date=" + date
        + ", homeWin=" + homeWin + ", draw=" + draw + ", awayWin=" + awayWin + ", under=" + under + ", over="
        + over + ", gol=" + gol + ", noGol=" + noGol + "]";
  }
  
}
