package activiti.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class IdentityRestClient extends ActivitiRestClient{

	public static String findUserPassword(String id){
		String infoUri = "service/identity/users/" + id + "/info/hashedPassword";
		Representation infoJSON;

		try {

			String uri = REST_URI + infoUri;
			infoJSON =  getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject info = new JSONObject(infoJSON.getText());
			return (String) info.get("value");

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createNewUser(String id, String password, String firstName, String lastName, String email){
		String uri = REST_URI + "service/identity/users";

		//kreiramo novog korisnika
		ObjectNode requestNode = objectMapper.createObjectNode();
		requestNode.put("id", id);
		if (password != null)
			requestNode.put("password", password);
		if (firstName != null)
			requestNode.put("firstName", firstName);
		if (lastName != null)
			requestNode.put("lastName", lastName);
		if (email != null)
			requestNode.put("email", email);

		try {
			getClientResource(uri).post(requestNode);
			System.out.println("korisnik " + id + " kreiran");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static List<String> listAllUsers(){
		String usersUri = "service/identity/users";
		String uri = REST_URI + usersUri;
		List<String> ret = new ArrayList<String>();
		Representation usersJSON;
		try {

			usersJSON = getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject users = new JSONObject(usersJSON.getText());
			JSONArray arr = (JSONArray) users.get("data");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject ob = (JSONObject) arr.get(i);
				ret.add((String) ob.get("id"));
			}
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void deleteUser(String id){
		String uri = REST_URI + "service/identity/users/" + id; //na kraju ide id korisnika
		try {
			getClientResource(uri).delete();
			System.out.println("korisnik " + id + " izbrisan");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void createNewGroup(String id, String name, String type){
		String uri = REST_URI + "service/identity/groups";

		//kreiramo novog korisnika
		ObjectNode requestNode = objectMapper.createObjectNode();
		requestNode.put("id", id);
		if (name != null)
			requestNode.put("name", name);
		if (type != null)
			requestNode.put("type", type);

		try {
			getClientResource(uri).post(requestNode);
			System.out.println("Grupa " + id + " kreirana");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static List<String> listAllGroups(){
		String usersUri = "service/identity/groups";
		String uri = REST_URI + usersUri;
		List<String> ret = new ArrayList<String>();
		Representation usersJSON;
		try {

			usersJSON = getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject users = new JSONObject(usersJSON.getText());
			JSONArray arr = (JSONArray) users.get("data");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject ob = (JSONObject) arr.get(i);
				ret.add((String) ob.get("id"));
			}
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void deleteGroup(String id){
		String uri = REST_URI + "service/identity/groups/" + id; //na kraju ide id grupe
		try {
			getClientResource(uri).delete();
			System.out.println("grupa " + id + " izbrisana");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void setUserInfo(String userId, String infoKey, String infoValue){
		String uri = REST_URI + "service/identity/users/" + userId + "/info";
		try{
			ObjectNode requestNode = objectMapper.createObjectNode();
			requestNode.put("key", infoKey);
			requestNode.put("value", infoValue);
			getClientResource(uri).post(requestNode);
			System.out.println("Postavljen " + infoKey + " za korisnika " + userId);
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createMembership(String groupId, String userId){
		String uri = REST_URI + "service/identity/groups/" + groupId + "/members";
		System.out.println(uri);
		try{
			ObjectNode requestNode = objectMapper.createObjectNode();
			requestNode.put("userId", userId);
			getClientResource(uri).post(requestNode);
			System.out.println("Korisnik " + userId + " dodat u grupu " + groupId);
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int usersCount(){
		String usersUri = "service/identity/users";
		String uri = REST_URI + usersUri;
		Representation usersJSON;
		try {
			usersJSON = getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject users = new JSONObject(usersJSON.getText());
			return  (int) users.get("total");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int groupsCount(){
		String groupsUri = "service/identity/groups";
		String uri = REST_URI + groupsUri;
		Representation groupsJSON;
		try {
			groupsJSON = getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject groups = new JSONObject(groupsJSON.getText());
			return  (int) groups.get("total");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int groupMembersCount(String groupId){
		String groupsUri = "service/identity/users?memberOfGroup=" + groupId; 
		String uri = REST_URI + groupsUri;
		Representation groupsJSON;
		try {
			groupsJSON = getClientResource(uri).get(MediaType.APPLICATION_JSON);
			JSONObject groups = new JSONObject(groupsJSON.getText());
			return  (int) groups.get("total");
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}


}
