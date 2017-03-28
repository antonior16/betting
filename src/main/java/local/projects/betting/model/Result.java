package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result extends Odds implements Serializable {
	private int goalsHomeTeam;
	private int goalsAwayTeam;
	private String sign;
	private String goalNoGol;
	private String underOver;
	private String score;
	private Boolean is2To4Multigol;
	private Boolean is2To3Multigol;

	public Result() {
	}

	public Result(Date scoreDate, Team homeTeamName, Team awayTeamName, int goalsHomeTeam, int goalsAwayTeam) {
		buildResult(goalsHomeTeam, goalsAwayTeam);
		this.setOddsDate(scoreDate);
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
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @return the goalNoGol
	 */
	public String getGoalNoGol() {
		return goalNoGol;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @return the underOver
	 */
	public String getUnderOver() {
		return underOver;
	}

	/**
	 * @param goalsHomeTeam
	 *            the goalsHomeTeam to set
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
	 *            the goalsAwayTeam to set
	 */
	public void setGoalsAwayTeam(int goalsAwayTeam) {
		this.goalsAwayTeam = goalsAwayTeam;
	}

	public Boolean getIs2To4Multigol() {
		return is2To4Multigol;
	}

	public void setIs2To4Multigol(Boolean is2To4Multigol) {
		this.is2To4Multigol = is2To4Multigol;
	}

	public Boolean getIs2To3Multigol() {
		return is2To3Multigol;
	}

	public void setIs2To3Multigol(Boolean is2To3Multigol) {
		this.is2To3Multigol = is2To3Multigol;
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
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
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Result [goalsHomeTeam=" + goalsHomeTeam + ", goalsAwayTeam=" + goalsAwayTeam + "]";
	}

	private void buildResult(int goalsHomeTeam, int goalsAwayTeam) {
		if (goalsHomeTeam > goalsAwayTeam) {
			this.sign = "1";
		} else {
			this.sign = "2";
		}

		if ((goalsHomeTeam - goalsAwayTeam) == 0) {
			this.sign = "X";
		}

		// Setting Gol/NoGol

		if (goalsHomeTeam > 0 & goalsAwayTeam > 0) {
			this.goalNoGol = "GOL";
		} else {
			this.goalNoGol = "NOGOL";
		}

		// Setting Under/Over
		if (goalsHomeTeam + goalsAwayTeam >= 3) {
			this.underOver = "OVER";
		} else {
			this.underOver = "UNDER";
		}
		if (goalsHomeTeam > goalsAwayTeam) {
			this.sign = "1";
		} else {
			this.sign = "2";
		}

		int totGol = goalsHomeTeam + goalsAwayTeam;

		if (totGol > 1) {
			if (totGol <= 4) {
				setIs2To4Multigol(true);
			}

			if (totGol <= 3) {
				setIs2To3Multigol(true);
			}
		}

		this.score = goalsHomeTeam + ":" + goalsAwayTeam;
	}
}
