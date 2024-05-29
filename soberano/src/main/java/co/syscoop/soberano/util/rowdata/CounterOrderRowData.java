package co.syscoop.soberano.util.rowdata;

public class CounterOrderRowData {
	
	private String counterCode = "";
	private Integer orderId = 0;
	private String orderLabel = "";
	
	public CounterOrderRowData(String counterCode) {
		this.setCounterCode(counterCode);
	}

	public String getCounterCode() {
		return counterCode;
	}

	public void setCounterCode(String counterCode) {
		this.counterCode = counterCode;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderLabel() {
		return orderLabel;
	}

	public void setOrderLabel(String orderLabel) {
		this.orderLabel = orderLabel;
	}
}
