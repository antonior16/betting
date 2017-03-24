package local.projects.betting.model;

import java.io.Serializable;
import java.util.logging.Logger;

public class League implements Serializable {
	private static final long serialVersionUID = -2308174004403590109L;
	private String name;
	private String oddsUrl;
	private String scoresUrl;

	public League(String name) {
		super();
		this.name = name;
	}

	public League(String name, String oddsUrl) {
		super();
		this.name = name;
		this.oddsUrl = oddsUrl;
	}

	public League(String name, String oddsUrl, String scoresUrl) {
		super();
		this.name = name;
		this.oddsUrl = oddsUrl;
		this.scoresUrl = scoresUrl;
	}

	public League() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOddsUrl() {
		return oddsUrl;
	}

	public void setOddsUrl(String oddsUrl) {
		this.oddsUrl = oddsUrl;
	}

	public String getScoresUrl() {
		return scoresUrl;
	}

	public void setScoresUrl(String scoresUrl) {
		this.scoresUrl = scoresUrl;
	}

	@Override
	public String toString() {
		return "League [name=" + name + ", booksUrl=" + oddsUrl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oddsUrl == null) ? 0 : oddsUrl.hashCode());
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
		if (oddsUrl == null) {
			if (other.oddsUrl != null)
				return false;
		} else if (!oddsUrl.equals(other.oddsUrl))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
