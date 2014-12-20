package activiti.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ActivitiRestClient {

	protected static final String REST_URI = "http://localhost:8090/activiti-rest/";
	protected static final ObjectMapper objectMapper = new ObjectMapper();
	private static String restUsername, restPassword;
	
	static {
		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream("properties/rest.properties");;
			Properties prop = new Properties();
			prop.load(stream);
			restUsername = prop.getProperty("restUsername");
			restPassword = prop.getProperty("restPassword");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static ClientResource getClientResource(String uri) throws IOException{
		ClientResource clientResource = new ClientResource(uri);
		clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, restUsername, restPassword);
		return clientResource;
	}
	
}
