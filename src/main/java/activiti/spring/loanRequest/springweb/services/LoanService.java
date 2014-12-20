package activiti.spring.loanRequest.springweb.services;

import activiti.spring.loanRequest.enitity.Loan;

public class LoanService {

	//@PersistenceContext
//	private EntityManager entityManager;
	
	public Loan buildLoan(String clientsName,String clientsSurname,
			 String clientsEmail,Long clientsIncome,Long requestedLoan,Boolean creditOk){
		
		 Loan loan = new Loan();
		 loan.setClientsEmail(clientsEmail);
		 loan.setClientsIncome(clientsIncome);
		 loan.setClientsName(clientsName);
		 loan.setClientsSurname(clientsSurname);
		 loan.setCreditOk(creditOk);
		 loan.setRequestedLoan(requestedLoan);
		 
		// entityManager.persist(loan);
		 
		 return loan;
		
	}
}
