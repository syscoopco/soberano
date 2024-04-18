package co.syscoop.soberano.util.rowdata;

public class CounterRowData {
	
	private String counterCode = "";
	private Integer numberOfReceivers = 0;
	private Boolean isSurcharged = false;
	private Boolean isEnabled = false;
	private Boolean isFree = true;
	
	public CounterRowData(String counterCode) {
		this.setCounterCode(counterCode);
	}

	public String getCounterCode() {
		return counterCode;
	}

	public void setCounterCode(String counterCode) {
		this.counterCode = counterCode;
	}

	public Integer getNumberOfReceivers() {
		return numberOfReceivers;
	}

	public void setNumberOfReceivers(Integer numberOfReceivers) {
		this.numberOfReceivers = numberOfReceivers;
	}

	public Boolean getIsSurcharged() {
		return isSurcharged;
	}

	public void setIsSurcharged(Boolean isSurcharged) {
		this.isSurcharged = isSurcharged;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}
}
