package local.projects.betting;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import local.projects.betting.api.DataEntry;
import local.projects.betting.model.League;

/**
 * Hello world!
 */
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
	private ObjectMapper objectMapper;

	@Autowired
	private JacksonJsonProvider jacksonJsonProvider;

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		Start3 p = context.getBean(Start3.class);
		p.getJacksonJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// p.extractResults(null);
		// p.extractOdds();
		p.getClient().path("/competitions/456").accept(MediaType.APPLICATION_JSON_TYPE);
		League league = p.getClient().get(League.class);
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

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public JacksonJsonProvider getJacksonJsonProvider() {
		return jacksonJsonProvider;
	}

	public void setJacksonJsonProvider(JacksonJsonProvider jacksonJsonProvider) {
		this.jacksonJsonProvider = jacksonJsonProvider;
	}

}
