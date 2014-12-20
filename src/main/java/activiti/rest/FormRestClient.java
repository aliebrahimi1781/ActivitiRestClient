package activiti.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FormRestClient extends ActivitiRestClient{


	public static List<Map<String, Object>> formProperties(String id, boolean task){

		String parameters;
		if (task)
			parameters = "taskId=" + id;
		else
			parameters = "processDefinitionId=" + id;


		String formUri  = "service/form/form-data?" + parameters;
		Representation formJSON;
		try {

			String uri = REST_URI + formUri;
			formJSON =  getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject form = new JSONObject(formJSON.getText());
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			JSONArray arr = (JSONArray) form.get("formProperties");
			for (int i = 0; i < arr.length(); i++) {
				Map<String, Object> currentFormParameter = new HashMap<String, Object>();
				JSONObject ob = (JSONObject) arr.get(i);
				currentFormParameter.put("id", ob.get("id"));
				currentFormParameter.put("name", ob.get("name"));
				currentFormParameter.put("type", ob.get("type"));
				currentFormParameter.put("value", ob.get("value"));
				currentFormParameter.put("readable", ob.get("readable"));
				currentFormParameter.put("writable", ob.get("writable"));
				currentFormParameter.put("required", ob.get("required"));
				currentFormParameter.put("datePattern", ob.get("datePattern"));
				ret.add(currentFormParameter);
				System.out.println(currentFormParameter);
			}

			return ret;

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static boolean submitStartFormData(String processDefId, Map<String, String> params){


		String uri = REST_URI + "service/form/form-data";
		ObjectNode requestNode = objectMapper.createObjectNode();
		requestNode.put("processDefinitionId", processDefId);
		if (params != null && params.size() > 0){
			//pravimo parametre
			ArrayNode propertyArray = objectMapper.createArrayNode();
			requestNode.put("properties", propertyArray);

			for (String key : params.keySet()){
				ObjectNode propNode  = new ObjectMapper().createObjectNode();
				propNode .put("id", key);
				propNode .put("value", params.get(key));
				propertyArray.add(propNode);
			}
		}



		System.out.println(requestNode);
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

	public static String getFormKey(String id, boolean task){

		String parameters;
		if (task)
			parameters = "taskId=" + id;
		else
			parameters = "processDefinitionId=" + id;


		String formUri  = "service/form/form-data?" + parameters;
		Representation formJSON;
		try {

			String uri = REST_URI + formUri;
			formJSON =  getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject form = new JSONObject(formJSON.getText());
			return form.getString("formKey");

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args){
		Map<String, String> test = new HashMap<String, String>();
		test.put("clientsName", "korisnik");
		test.put("clientsSurname", "korisnik");
		test.put("clientsEmail", "korisnik@localhost");
		test.put("clientsIncome", "100");
		test.put("requestedLoan", "30");
		submitStartFormData("loanRequest:1:2504", test);
	}
}
