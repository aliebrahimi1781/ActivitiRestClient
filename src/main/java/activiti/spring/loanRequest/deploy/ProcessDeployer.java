package activiti.spring.loanRequest.deploy;
import java.io.File;
import java.net.URISyntaxException;

import activiti.rest.RepositoryRestClient;


/**
 * Klasa zaduzena za deployment
 * @author xyz
 *
 */
public class ProcessDeployer {

	private static final String filename = "diagrams/LoanRequest.bpmn";

	public static void main (String[] args){

		try {
			File f = new File(ClassLoader.getSystemResource(filename).toURI());
			RepositoryRestClient.deployProcessDefinition(f);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}




}
