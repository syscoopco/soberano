package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.PrintableDataMapper;
import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

public class ProcessRunOutputAllocation extends BusinessActivityTrackedObject {
	
	private Integer productionLineId = 0;
	private String description = "";
	private String itemName = "";
	private Integer orderId = 0;
	
	public Integer getProductionLineId() {
		return productionLineId;
	}

	public void setProductionLineId(Integer productionLineId) {
		this.productionLineId = productionLineId;
	}

	public ProcessRunOutputAllocation() {}
	
	public ProcessRunOutputAllocation(Integer produtionLineId) {
		this.productionLineId = produtionLineId;
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_ProcessRunOutputAllocation_getAll\"" 
							+ "(:productionLineId, :lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_ProcessRunOutputAllocation_getCount\"(:productionLineId, :lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("productionLineId", productionLineId);	
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());
	}
	
	@Override
	public void get() throws SQLException {
	}

	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return super.getCount();
	}
	
	@Override
	public Integer record() throws Exception {
		return null;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
		return null;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}

	public Integer markAllocationAsProduced(Integer allocationId) throws SQLException, Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRunOutputAllocation_markAsProduced\"(:allocationId, :loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("allocationId", allocationId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer markAllocationAsOmitted(Integer allocationId) throws SQLException, Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRunOutputAllocation_markAsOmitted\"(:allocationId, :loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("allocationId", allocationId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	@Override
	public PrintableData getReportFull() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRunOutputAllocation_getReport\"(:opId, "
							+ "								:lang, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("opId", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(qryStr, parametersMap, new PrintableDataMapper()).get(0);
	}
	
	@Override
	public PrintableData getReportMinimal() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRunOutputAllocation_getReportMinimal\"(:opId, "
							+ "								:lang, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("opId", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(qryStr, parametersMap, new PrintableDataMapper()).get(0);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
