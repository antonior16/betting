package local.projects.betting;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import local.projects.betting.api.DataEntry;
import local.projects.betting.api.rest.client.TeamsCollection;

@Component
public class Start3 {
	@Resource(name = "odds")
	private DataEntry oddsDataEntry;

	@Resource
	private WebClient client;

	@Resource(name = "results")
	private DataEntry resultsDataEntry;

	private static final Logger LOGGER = LoggerFactory.getLogger(Start3.class);

	@Autowired
	private JacksonJsonProvider jacksonJsonProvider;

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		Start3 p = context.getBean(Start3.class);
		p.getJacksonJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		p.getJacksonJsonProvider().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,false);

		// p.extractResults(null);
		// p.extractOdds();

		ClientConfiguration config = WebClient.getConfig(p.getClient());
		config.getInInterceptors().add(new LoggingInInterceptor());
		config.getOutInterceptors().add(new LoggingOutInterceptor());

		p.getClient().path("/competitions/456/teams").accept(MediaType.APPLICATION_JSON_TYPE);
		TeamsCollection result = p.getClient().get(TeamsCollection.class);

		// List<Team> result = (List<Team>)
		// p.getClient().getCollection(Team.class);

		// p.getClient().path("/competitions/456/fixtures").accept(MediaType.APPLICATION_JSON_TYPE);
		// p.getClient().query("timeFrameStart", "2017-09-24");
		// p.getClient().query("timeFrameEnd", "2017-09-24");
		//
		// List<Fixture> result = (List<Fixture>)
		// p.getClient().getCollection(Fixture.class);

		System.out.println(result);

	}

	/**
	 * @param s
	 */
	private void extractOdds() {
		oddsDataEntry.extractOdds();
	}

	private void extractResults(String timeFrame) {
		resultsDataEntry.extractResults(timeFrame);
	}

	public WebClient getClient() {
		return client;
	}

	public void setClient(WebClient client) {
		this.client = client;
	}

	public JacksonJsonProvider getJacksonJsonProvider() {
		return jacksonJsonProvider;
	}

	public void setJacksonJsonProvider(JacksonJsonProvider jacksonJsonProvider) {
		this.jacksonJsonProvider = jacksonJsonProvider;
	}

}
