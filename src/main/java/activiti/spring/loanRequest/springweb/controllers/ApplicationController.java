package activiti.spring.loanRequest.springweb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import activiti.rest.FormRestClient;
import activiti.rest.RepositoryRestClient;
import activiti.rest.RuntimeRestClient;

@Controller
@RequestMapping(value="/application")
public class ApplicationController {


	@RequestMapping(value="/tasksList", method = RequestMethod.GET)
	public String showUsersTasks(ModelMap model) {

		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return "redirect:/login";
		}

		String id = user.getUsername();


		//		List<Task> myTasks = taskService.createTaskQuery().taskAssignee(id).list();
		//		model.addAttribute("myTasks", myTasks);
		//
		//		List<Task> candidateTasks = taskService.createTaskQuery().taskCandidateUser(id).list();
		//		model.addAttribute("candidateTasks", candidateTasks);





		return "application/tasksList";
	}


	//	@RequestMapping(value="/claim/{taskId}", method = RequestMethod.GET)
	//	public String claimTask(@PathVariable String taskId, ModelMap model) {
	//	
	//		User user;
	//		try{
	//			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//		}
	//		catch(Exception ex){
	//			return "redirect:/login";
	//		}
	//		
	//		String message;
	//		String userId = user.getUsername();
	//		if (!canClaim(taskId, userId))
	//			message = "Ne možete prihvatiti zadatak";
	//		else{
	//			taskService.claim(taskId, userId);
	//			message = "Zadatak je uspešno prihvaćen";
	//		}
	//
	//		model.addAttribute("message", message);
	//
	//		return showUsersTasks(model);
	//
	//	}
	//
	//	@RequestMapping(value="/showTask/{taskId}", method = RequestMethod.GET)
	//	public String showTask(@PathVariable String taskId, ModelMap model){
	//		
	//		User user;
	//		try{
	//			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//		}
	//		catch(Exception ex){
	//			return "redirect:/login";
	//		}
	//		
	//		String message;
	//		String userId = user.getUsername();
	//		if (!canExecute(taskId, userId)){
	//			message = "Ne možete izvršiti zadatak";
	//			model.addAttribute("message", message);
	//			return showUsersTasks(model);
	//
	//		}
	//		else{
	//
	//			//preuzimamo podatke o formi
	//			TaskFormData taskFormData = formService.getTaskFormData(taskId);
	//			List<FormProperty> formProperties = taskFormData.getFormProperties();
	//
	//			if (formProperties.size() == 0){
	//				taskService.complete(taskId);
	//				message = "Zadatak uspešno izvršen";
	//				model.addAttribute("message", message);
	//				return showUsersTasks(model);
	//			}
	//			else{
	//
	//				model.addAttribute("formProperties", formProperties);
	//				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	//				model.addAttribute("task", task);
	//				
	//				//U definiciji procesa je definisan formKey, na osnovu kog se odredjuje 
	//				//koja se stranica prikazuje
	//				String form = formService.getTaskFormData(taskId).getFormKey();
	//				return "application/"+form;
	//			}
	//
	//		}
	//	}
	//
	//	@RequestMapping(value="/execute/{taskId}", method = RequestMethod.POST)
	//	public String execcuteTask(@PathVariable String taskId, @RequestParam Map<String, String> params, ModelMap model){
	//
	//		User user;
	//		try{
	//			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//		}
	//		catch(Exception ex){
	//			return "redirect:/login";
	//		}
	//		
	//		String userId = user.getUsername();
	//		String message;
	//		if (canExecute(taskId, userId)){
	//			//pre ovog koraka bi se trebala sprovesti validacija
	//			//da li su uneti svi potrebni parametri (required), da li ima neslaganja tipova
	//			//ako se unosi email adresa, da li je validna i sl.
	//			formService.submitTaskFormData(taskId, params);
	//			message = "Zadatak uspešno izvršen";
	//		}
	//		else
	//			message = "Ne možete izvršiti zadatak";
	//
	//		model.addAttribute("message", message);
	//		return showUsersTasks(model);
	//
	//	}
	//	
	@RequestMapping(value="/newInstance", method = RequestMethod.GET)
	public String newInstance(ModelMap model){

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null)
			return "redirect:/login";


		String processDefId = RepositoryRestClient.getProcessDefinition("loanRequest");

		List<Map<String, Object>> formProperties =  FormRestClient.formProperties(processDefId, false);
		String form = FormRestClient.getFormKey(processDefId, false);

		RuntimeRestClient.startProcessInstanceByKey("loanRequest");

		if (formProperties.size() == 0){
			boolean startedOk = RuntimeRestClient.startProcessInstanceByKey("loanRequest");
			String message;
			if (startedOk)
				message = "Nova instanca je uspešno pokrenuta";
			else
				message = "Došlo je do problema prilikom pokretanja instance";

			model.addAttribute("message", message);
			return printWelcome(model);
		}
		else{
			model.addAttribute("formProperties", formProperties);
			return "application/"+form;
		}

	}


	@RequestMapping(value="/startNewInstance", method = RequestMethod.POST)
	public String startNewInstance(ModelMap model, @RequestParam Map<String, String> params){

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null)
			return "redirect:/login";

		String processDefId = RepositoryRestClient.getProcessDefinition("loanRequest");

		//takodje bi sada ovde trebala biti uradjena validacija
		FormRestClient.submitStartFormData(processDefId, params);

		String message = "Nova instanca je uspešno pokrenuta";
		model.addAttribute("message", message);
		return printWelcome(model);

	}


	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return "redirect:/login";
		}

		String name = user.getUsername();
		model.addAttribute("username", name);
		return "application/welcome";

	}
	//
	//
	//	private boolean canClaim(String taskId, String userId){
	//		for (Task t : taskService.createTaskQuery().taskCandidateUser(userId).list())
	//			if (t.getId().equals(taskId))
	//				return true;
	//		return false;
	//	}
	//
	//	private boolean canExecute(String taskId, String userId){
	//		for (Task t : taskService.createTaskQuery().taskAssignee(userId).list())
	//			if (t.getId().equals(taskId))
	//				return true;
	//		return false;
	//	}
}
