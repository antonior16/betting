package local.projects.betting.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Odds implements Serializable {
	private static final long serialVersionUID = -6036961121582000654L;
	private Double homeWin;
	private Double draw;
	private Double awayWin;
	private Double under;
	private Double over;
	private Double gol;
	private Double noGol;

	public Odds() {

	}

	public Odds(Date oddsDate, Team homeTeamName, Team awayTeamName, Double homeWin, Double draw, Double awayWin) {
		this.homeWin = homeWin;
		this.draw = draw;
		this.awayWin = awayWin;
	}

	public Odds(Date oddsDate, Team homeTeamName, Team awayTeamName, Double homeWin, Double draw, Double awayWin,
			Double under, Double over, Double gol, Double noGol) {
		super();
		this.homeWin = homeWin;
		this.draw = draw;
		this.awayWin = awayWin;
		this.under = under;
		this.over = over;
		this.gol = gol;
		this.noGol = noGol;
	}

	public Odds(Double homeWin, Double draw, Double awayWin, Double under, Double over, Double gol, Double noGol) {
		super();
		this.homeWin = homeWin;
		this.draw = draw;
		this.awayWin = awayWin;
		this.under = under;
		this.over = over;
		this.gol = gol;
		this.noGol = noGol;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return result;
	}
}
