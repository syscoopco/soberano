package co.syscoop.soberano.domain.tracked;

import co.syscoop.soberano.domain.untracked.AcquisitionExpense;

public class ServiceAcquisition {
	
	private AcquisitionExpense expense = new AcquisitionExpense();
	private Service service = new Service();	
	
	public AcquisitionExpense getExpense() {
		return expense;
	}

	public void setExpense(AcquisitionExpense expense) {
		this.expense = expense;
	}
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
}
