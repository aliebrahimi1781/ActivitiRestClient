package activiti.rest;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.resource.ResourceException;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class RuntimeRestClient extends ActivitiRestClient{

	public static boolean startProcessInstanceByKey(String key){

		String uri = REST_URI + "service/runtime/process-instances";

		//kreiramo instancu
		ObjectNode requestNode = objectMapper.createObjectNode();
		requestNode.put("processDefinitionKey", key);

		try {
			getClientResource(uri).post(requestNode, MediaType.APPLICATION_JSON);
			return true;
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
