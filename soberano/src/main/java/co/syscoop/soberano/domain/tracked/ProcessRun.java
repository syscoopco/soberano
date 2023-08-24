package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.DaoBase;
import co.syscoop.soberano.database.relational.QueryResultMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.SpringUtility;

public class ProcessRun extends BusinessActivityTrackedObject {
	
	private Process process = new Process();
	private CostCenter costCenter = new CostCenter();
	private ArrayList<InventoryItem> inputItems = new ArrayList<InventoryItem>();
	private ArrayList<String> inputItemCodes = null;
	private ArrayList<BigDecimal> inputQuantities = null;
	private ArrayList<Unit> inputUnits = new ArrayList<Unit>();
	private ArrayList<Integer> inputUnitIds = null;
	private ArrayList<InventoryItem> outputItems = new ArrayList<InventoryItem>();
	private ArrayList<String> outputItemCodes = null;
	private ArrayList<BigDecimal> outputQuantities = null;
	private ArrayList<Unit> outputUnits = new ArrayList<Unit>();
	private ArrayList<Integer> outputUnitIds= null;
	private ArrayList<Integer> weights = null;	
	
	private void fillInputItemIds() {
		inputItemCodes = new ArrayList<String>();
		for (InventoryItem inventoryItem : inputItems) {
			inputItemCodes.add(inventoryItem.getStringId());
		}
	}
	
	private void fillOutputItemIds() {
		outputItemCodes = new ArrayList<String>();
		for (InventoryItem inventoryItem : outputItems) {
			outputItemCodes.add(inventoryItem.getStringId());
		}
	}
	
	private void fillInputUnitIds() {
		inputUnitIds = new ArrayList<Integer>();
		for (Unit unit : inputUnits) {
			inputUnitIds.add(unit.getId());
		}
	}
	
	private void fillOutputUnitIds() {
		outputUnitIds = new ArrayList<Integer>();
		for (Unit unit : outputUnits) {
			outputUnitIds.add(unit.getId());
		}
	}
	
	public ProcessRun() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_ProcessRun_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_ProcessRun_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public ProcessRun(Integer id) {
		this.setId(id);
	}
	
	public ProcessRun(Integer process,
						Integer costCenter,
						ArrayList<InventoryItem> inputItems,
						ArrayList<BigDecimal> inputQuantities,
						ArrayList<Unit> inputUnits,
						ArrayList<InventoryItem> outputItems,
						ArrayList<BigDecimal> outputQuantities,
						ArrayList<Unit> outputUnits,
						ArrayList<Integer> weights) {
		super.setOccurrenceTime(new Date());
		this.process.setId(process);
		this.costCenter.setId(costCenter);
		this.inputItems = inputItems;
		fillInputItemIds();	
		this.inputUnits = inputUnits;
		fillInputUnitIds();	
		this.outputQuantities = outputQuantities;
		this.outputItems = outputItems;
		fillOutputItemIds();	
		this.outputUnits = outputUnits;
		fillOutputUnitIds();	
		this.outputQuantities = outputQuantities;
		this.weights = weights;
	}

	@Override
	public void get() throws SQLException {
	}

	@Override
	public Integer print() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
	
	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return super.getAll(orderByColumn, descOrder, limit, offset, extractor);
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return super.getCount();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_ProcessRun_create\"(:process, "
				+ "											:costCenter, "
				+ "											:inputItems, "
				+ "											:inputQuantities, "
				+ "											:inputUnits, "				
				+ "											:outputItems, "
				+ "											:outputQuantities, "
				+ "											:outputUnits, "
				+ "											:weights, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("process", this.process.getId());
		recordParameters.addValue("costCenter", this.costCenter.getId());
		recordParameters.addValue("inputItems", createArrayOfSQLType("varchar", this.inputItemCodes.toArray()));
		recordParameters.addValue("inputQuantities", createArrayOfSQLType("numeric", this.inputQuantities.toArray()));
		recordParameters.addValue("inputUnits", createArrayOfSQLType("integer", this.inputUnits.toArray()));
		recordParameters.addValue("outputItems", createArrayOfSQLType("varchar", this.outputItemCodes.toArray()));
		recordParameters.addValue("outputQuantities", createArrayOfSQLType("numeric", this.outputQuantities.toArray()));
		recordParameters.addValue("outputUnits", createArrayOfSQLType("integer", this.outputUnits.toArray()));
		recordParameters.addValue("weights", this.weights);
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_ProcessRun_disable\"(:processRunId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("processRunId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	public Integer cancel() throws SQLException, Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRun_cancel\"(:processRunId, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return ((new DaoBase()).query(qryStr, parametersMap , new QueryResultMapper()).get(0));
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}

	public ArrayList<BigDecimal> getInputQuantities() {
		return inputQuantities;
	}

	public void setInputQuantities(ArrayList<BigDecimal> inputQuantities) {
		this.inputQuantities = inputQuantities;
	}

	public ArrayList<BigDecimal> getOutputQuantities() {
		return outputQuantities;
	}

	public void setOutputQuantities(ArrayList<BigDecimal> outputQuantities) {
		this.outputQuantities = outputQuantities;
	}

	public ArrayList<Integer> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Integer> weights) {
		this.weights = weights;
	}
}
