package co.syscoop.soberano.util.rowdata;

import java.math.BigDecimal;

public class CatalogEntryRowData {
	private Integer itemId = 0;
	private Integer entityTypeInstanceId = 0;
	private String itemName = "";
	private String categoryName = "";
	private Boolean itemEnabled = false;
	private BigDecimal itemPrice = new BigDecimal(0);
	private BigDecimal itemReferencePrice = new BigDecimal(0);
	private String sysCurrency = "";
	private String refCurrency = "";
	private BigDecimal exchRate = new BigDecimal(0);
	
	public Integer getEntityTypeInstanceId() {
		return entityTypeInstanceId;
	}

	public void setEntityTypeInstanceId(Integer entityTypeInstanceId) {
		this.entityTypeInstanceId = entityTypeInstanceId;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Boolean getItemEnabled() {
		return itemEnabled;
	}

	public void setItemEnabled(Boolean itemEnabled) {
		this.itemEnabled = itemEnabled;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getItemReferencePrice() {
		return itemReferencePrice;
	}

	public void setItemReferencePrice(BigDecimal itemReferencePrice) {
		this.itemReferencePrice = itemReferencePrice;
	}

	public String getSysCurrency() {
		return sysCurrency;
	}

	public void setSysCurrency(String sysCurrency) {
		this.sysCurrency = sysCurrency;
	}

	public String getRefCurrency() {
		return refCurrency;
	}

	public void setRefCurrency(String refCurrency) {
		this.refCurrency = refCurrency;
	}

	public BigDecimal getExchRate() {
		return exchRate;
	}

	public void setExchRate(BigDecimal exchRate) {
		this.exchRate = exchRate;
	}
}
