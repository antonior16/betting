package local.projects.betting.data.extract.football.data.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import local.projects.betting.api.data.extract.FixtureDataExtract;
import local.projects.betting.dao.FixtureDao;
import local.projects.betting.dao.LeagueDao;
import local.projects.betting.data.extract.selenium.web.driver.model.WebDriverEnum;
import local.projects.betting.model.Fixture;
import local.projects.betting.model.League;
import local.projects.betting.model.json.FixturesCollection;

public class FootballDataDataExtractImpl implements FixtureDataExtract {
	@Autowired
	private WebClient footballDataRestClient;

	@Autowired
	public FixtureDao resultDao;

	private Date scoreDate;

	public FootballDataDataExtractImpl() {
	}

	public FootballDataDataExtractImpl(WebDriverEnum webDriver) {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(FootballDataDataExtractImpl.class);

	@Override
	public List<Fixture> extractFixtures(League league, String timeFrame) {
		List<Fixture> result = new ArrayList<Fixture>();

		LOGGER.info("Define path for " + league.getName() + ":" + league.getScoresUrl());
		// footballDataRestClient.path(null);
		footballDataRestClient.reset().path(league.getScoresUrl()).accept(MediaType.APPLICATION_JSON_TYPE);
		footballDataRestClient.query("timeFrameStart", timeFrame);
		footballDataRestClient.query("timeFrameEnd", timeFrame);
		try {
			LOGGER.info("Executing rest client invokation for: " + footballDataRestClient.getBaseURI()
					+ footballDataRestClient.getBaseURI().getPath());
			FixturesCollection fixturesCollection = footballDataRestClient.get(FixturesCollection.class);

			List<Fixture> fixturesList = fixturesCollection.getFixtures();

			if (fixturesList != null && !fixturesList.isEmpty()) {
				for (Fixture fixture : fixturesList) {
					fixture.getResult().buildResult();
					result.add(fixture);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"An exception has occurred getting fixtures from: " + footballDataRestClient.getBaseURI().getPath()
							+ "/" + footballDataRestClient.getBaseURI().getPath());
			throw e;
		}
		return result;
	}

	private Date getResultDate(String time) throws ParseException {
		int day = Integer.parseInt(time.substring(0, 2));
		int month = Integer.parseInt(time.substring(3, 5));

		String stringDate = day + "/" + String.format("%02d", month) + "/" + "2017";
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

		Date scoreDate = format.parse(stringDate);

		return scoreDate;
	}

	private long getDiffDays(String timeFrame) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		long dayDiff = 0;
		try {
			scoreDate = sdf.parse(timeFrame);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long diff = new Date().getTime() - scoreDate.getTime();
		dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		return dayDiff;
	}
}
