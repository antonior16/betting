package local.projects.betting.model;

import java.io.Serializable;
import java.util.logging.Logger;

public class League implements Serializable {
  
  @Override
  public String toString() {
    return "League [name=" + name + ", booksUrl=" + booksUrl + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((booksUrl == null) ? 0 : booksUrl.hashCode());
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
    League other = (League) obj;
    if (booksUrl == null) {
      if (other.booksUrl != null)
        return false;
    } else if (!booksUrl.equals(other.booksUrl))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  private static final long serialVersionUID = -2308174004403590109L;
  private String name;
  private String booksUrl;
  
  public League(String name, String booksUrl) {
    super();
    this.name = name;
    this.booksUrl = booksUrl;
  }
  
  public League() {
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getBooksUrl() {
    return booksUrl;
  }
  
  public void setBooksUrl(String booksUrl) {
    this.booksUrl = booksUrl;
  }
}
