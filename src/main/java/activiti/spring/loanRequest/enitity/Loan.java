package activiti.spring.loanRequest.enitity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity	
public class Loan {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String clientsName;
	private String clientsSurname;
	private String clientsEmail;
	private Long clientsIncome;
	private Long requestedLoan;
	private Boolean creditOk;
	
	
	public Loan(){
		
	}
	
	public Loan(String clientsName, String clientsSurname, String clientsEmail,
			Long clientsIncome, Long requestedLoan, Boolean creditOk) {
		super();
		this.clientsName = clientsName;
		this.clientsSurname = clientsSurname;
		this.clientsEmail = clientsEmail;
		this.clientsIncome = clientsIncome;
		this.requestedLoan = requestedLoan;
		this.creditOk = creditOk;
	}
	
	
	public String getClientsName() {
		return clientsName;
	}
	public void setClientsName(String clientsName) {
		this.clientsName = clientsName;
	}
	public String getClientsSurname() {
		return clientsSurname;
	}
	public void setClientsSurname(String clientsSurname) {
		this.clientsSurname = clientsSurname;
	}
	public String getClientsEmail() {
		return clientsEmail;
	}
	public void setClientsEmail(String clientsEmail) {
		this.clientsEmail = clientsEmail;
	}

	public Boolean getCreditOk() {
		return creditOk;
	}
	public void setCreditOk(Boolean creditOk) {
		this.creditOk = creditOk;
	}


	public Long getClientsIncome() {
		return clientsIncome;
	}


	public void setClientsIncome(Long clientsIncome) {
		this.clientsIncome = clientsIncome;
	}


	public Long getRequestedLoan() {
		return requestedLoan;
	}


	public void setRequestedLoan(Long requestedLoan) {
		this.requestedLoan = requestedLoan;
	}
	
	
	

}
