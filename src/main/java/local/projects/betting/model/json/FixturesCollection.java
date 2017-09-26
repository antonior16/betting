package local.projects.betting.model.json;

import java.util.List;

import local.projects.betting.model.Fixture;

public class FixturesCollection {
	private List<Fixture> fixtures;

	public FixturesCollection() {
	}

	public List<Fixture> getFixtures() {
		return fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}
}
