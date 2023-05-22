package co.syscoop.soberano.domain.untracked;

import co.syscoop.soberano.domain.tracked.Provider;

public class AcquisitionExpense extends Expense {

	private Provider provider = new Provider();
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
}
