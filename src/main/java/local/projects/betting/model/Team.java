package local.projects.betting.model;

import java.io.Serializable;

public class Team implements Serializable {
  
  public Team() {
  }
  
  @Override
  public String toString() {
    return "Team [name=" + name + ", flag=" + flag + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((flag == null) ? 0 : flag.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Team other = (Team) obj;
    if (flag == null) {
      if (other.flag != null)
        return false;
    } else if (!flag.equals(other.flag))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  public Team(String name) {
    super();
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getFlag() {
    return flag;
  }
  
  public void setFlag(String flag) {
    this.flag = flag;
  }
  
  private String name;
  private String flag;
}
