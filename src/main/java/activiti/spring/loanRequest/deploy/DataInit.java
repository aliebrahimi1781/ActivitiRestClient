package activiti.spring.loanRequest.deploy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sourceforge.yamlbeans.YamlException;
import net.sourceforge.yamlbeans.YamlReader;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import activiti.rest.IdentityRestClient;

/**
 * Ucitava korisnike i grupe iz yml fajlova
 * Vise o koriscenoj biblioteci: http://yamlbeans.sourceforge.net/
 * @author xyz
 *
 */
public class DataInit {

	private static final String groupsPath ="./src/main/resources/properties/groups.yml";
	private static final String usersPath ="./src/main/resources/properties/users.yml";
	private static String restUserId;
	
	static {
		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream("properties/rest.properties");;
			Properties prop = new Properties();
			prop.load(stream);
			restUserId = prop.getProperty("restUsername");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kreira grupe na osnovu podataka iz yml fajla 
	 */
	@SuppressWarnings("rawtypes")
	private static void initGroupsYml(){
		deleteAllGroups();
		YamlReader reader = null;
		Map map;
		try {
			reader = new YamlReader(new FileReader(groupsPath));
			while (true) {
				map = (Map) reader.read();
				if (map == null)
					break;
				IdentityRestClient.createNewGroup((String)map.get("id"), (String) map.get("name"), (String) map.get("type"));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (YamlException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kreira korisnike i clanstva grupama na osnovu podataka iz yml fajla 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initUsersYml(){
		deleteAllUsers();
		YamlReader reader = null;
		Map map;
		try {
			reader = new YamlReader(new FileReader(usersPath));
			while (true) {
				map = (Map) reader.read();
				if (map == null)
					break;

				String userId = (String) map.get("id");
				IdentityRestClient.createNewUser(userId, null, 
						(String) map.get("firstName"), (String) map.get("lastName"), (String) map.get("email"));

				String password = (String) map.get("password");
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(password);
				IdentityRestClient.setUserInfo(userId, "hashedPassword", hashedPassword);


				for (HashMap recordMap : (List<HashMap>) map.get("groups")){
					IdentityRestClient.createMembership((String) recordMap.get("id"), userId);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (YamlException e) {
			e.printStackTrace();
		}
	}


	private static void deleteAllUsers(){
		List<String> userIds = IdentityRestClient.listAllUsers();
		for (String id : userIds)
			if (! id.equals(restUserId))
				IdentityRestClient.deleteUser(id);
	}

	private static void deleteAllGroups(){
		List<String> groupIds = IdentityRestClient.listAllGroups();
		for (String id : groupIds)
			IdentityRestClient.deleteGroup(id);
	}

	public static void main(String[] args){
		initGroupsYml();
		initUsersYml();

		System.out.println("Broj grupa: " + IdentityRestClient.groupsCount());
		System.out.println("Broj korisnika: " + IdentityRestClient.usersCount());
		System.out.println("Broj korisnika u grupi bankar:  " + IdentityRestClient.groupMembersCount("bankar"));
	}


}
