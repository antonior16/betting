package local.projects.betting.data.entry.api.football.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
    ignoreUnknown = true)
public class Timeframe implements Serializable {
  private Fixture[] fixtures;
  
  public Timeframe() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  /**
   * @return the fixtures
   */
  public Fixture[] getFixtures() {
    return fixtures;
  }
  
  /**
   * @param fixtures
   *          the fixtures to set
   */
  public void setFixtures(Fixture[] fixtures) {
    this.fixtures = fixtures;
  }
}
