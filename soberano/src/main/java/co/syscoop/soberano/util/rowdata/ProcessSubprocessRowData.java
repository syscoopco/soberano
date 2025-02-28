package co.syscoop.soberano.util.rowdata;

public class ProcessSubprocessRowData {
	
	private Integer itemId = 0;
	private String itemName = "";
	
	public ProcessSubprocessRowData(){};
	
	public ProcessSubprocessRowData(Integer itemId, String itemName) {
		this.setItemId(itemId);
		this.setItemName(itemName);
	}
	
	public Integer getItemId() {
		return itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
