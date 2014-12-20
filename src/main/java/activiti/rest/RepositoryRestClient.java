package activiti.rest;

import java.io.File;
import java.io.IOException;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.html.FormData;
import org.restlet.ext.html.FormDataSet;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class RepositoryRestClient extends ActivitiRestClient {

	public static void deployProcessDefinition(File file){
		String uri = REST_URI + "service/repository/deployments";
		FormDataSet form = new FormDataSet();
		Representation fileRep = new FileRepresentation(file, MediaType.TEXT_XML);
		form.setMultipart(true);
		form.getEntries().add(new FormData("LoanRequest.bpmn", fileRep));

		try {
			getClientResource(uri).post(form);
			System.out.println("Definicija procesa usesno deploy-ovana");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProcessDefinition(String key){
		String definitionUri  = "service/repository/process-definitions?key=" + key + "&latest=true";
		Representation defJSON;
		try {

			String uri = REST_URI + definitionUri;
			defJSON =  getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject definitions = new JSONObject(defJSON.getText());

			JSONArray arr = (JSONArray) definitions.get("data");
			//vratice samo jednu definiciju jer smo poslali parametar latest (samo poslednju)

			JSONObject  definition = (JSONObject) arr.get(0);
			return (String) definition.get("id");

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
