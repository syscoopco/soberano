package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.database.relational.ObjectMapper;
import co.syscoop.soberano.database.relational.ProcessIOMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ProcessRunningException;

public class Process extends TrackedObject {

	private BigDecimal fixedCost = new BigDecimal(0.0);
	private ArrayList<InventoryItem> inputInventoryItems = new ArrayList<InventoryItem>();
	private ArrayList<String> inputInventoryItemCodes = null;
	private ArrayList<Unit> inputUnits = new ArrayList<Unit>();
	private ArrayList<Integer> inputUnitIds = null;
	private ArrayList<BigDecimal> inputQuantities = new ArrayList<BigDecimal>();
	private ArrayList<InventoryItem> outputInventoryItems = new ArrayList<InventoryItem>();
	private ArrayList<String> outputInventoryItemCodes = null;
	private ArrayList<Unit> outputUnits = new ArrayList<Unit>();
	private ArrayList<Integer> outputUnitIds = null;
	private ArrayList<BigDecimal> outputQuantities = new ArrayList<BigDecimal>();
	private ArrayList<Integer> weights = new ArrayList<Integer>();
	
	private void fillInputInventoryItemIds() {
		inputInventoryItemCodes = new ArrayList<String>();
		for (InventoryItem inventoryItem : inputInventoryItems) {
			inputInventoryItemCodes.add(inventoryItem.getStringId());
		}
	}
	
	private void fillInputUnitIds() {
		inputUnitIds = new ArrayList<Integer>();
		for (Unit unit : inputUnits) {
			inputUnitIds.add(unit.getId());
		}
	}
	
	private void fillOutputInventoryItemIds() {
		outputInventoryItemCodes = new ArrayList<String>();
		for (InventoryItem inventoryItem : outputInventoryItems) {
			outputInventoryItemCodes.add(inventoryItem.getStringId());
		}
	}
	
	private void fillOutputUnitIds() {
		outputUnitIds = new ArrayList<Integer>();
		for (Unit unit : outputUnits) {
			outputUnitIds.add(unit.getId());
		}
	}
	
	public Process(Integer id) {
		super(id);
	}
	
	public Process(Integer id, String name) {
		super(id, name);
	}
	
	public Process(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			BigDecimal fixedCost) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);		
		this.setFixedCost(fixedCost);
	}
	
	public Process(Integer id, 
					Integer entityTypeInstanceId, 
					String name,
					BigDecimal fixedCost,
					ArrayList<InventoryItem> inputInventoryItems,
					ArrayList<Unit> inputUnits,
					ArrayList<BigDecimal> inputQuantities,
					ArrayList<InventoryItem> outputInventoryItems,
					ArrayList<Unit> outputUnits,
					ArrayList<BigDecimal> outputQuantities,
					ArrayList<Integer> weights) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);		
		this.setFixedCost(fixedCost);
		this.inputInventoryItems = inputInventoryItems;
		fillInputInventoryItemIds();	
		this.inputUnits = inputUnits;
		fillInputUnitIds();	
		this.inputQuantities = inputQuantities;
		this.outputInventoryItems = outputInventoryItems;
		fillOutputInventoryItemIds();	
		this.outputUnits = outputUnits;
		fillOutputUnitIds();	
		this.outputQuantities = outputQuantities;
		this.weights = weights;
	}
	
	public Process() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Process_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Process_create\"(:processName, "
				+ "											:fixedCost, "				
				+ "											:inputItems, "
				+ "											:inputQuantities, "
				+ "											:inputUnits, "
				+ "											:outputItems, "
				+ "											:outputQuantities, "
				+ "											:outputUnits, "
				+ "											:weights, "				
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("processName", this.getName());
		recordParameters.addValue("fixedCost", this.getFixedCost());	
		
		recordParameters.addValue("inputItems", createArrayOfSQLType("varchar", this.getInputInventoryItemCodes().toArray()));
		recordParameters.addValue("inputQuantities", createArrayOfSQLType("numeric", this.inputQuantities.toArray()));
		recordParameters.addValue("inputUnits", createArrayOfSQLType("integer", this.getInputUnitIds().toArray()));
		
		recordParameters.addValue("outputItems", createArrayOfSQLType("varchar", this.getOutputInventoryItemCodes().toArray()));
		recordParameters.addValue("outputQuantities", createArrayOfSQLType("numeric", this.outputQuantities.toArray()));
		recordParameters.addValue("outputUnits", createArrayOfSQLType("integer", this.getOutputUnitIds().toArray()));
		recordParameters.addValue("weights", createArrayOfSQLType("integer", this.getWeights().toArray()));
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Process_modify\"(:processId, "
						+ " 								:processName, "
						+ "									:fixedCost, "				
						+ "									:inputItems, "
						+ "									:inputQuantities, "
						+ "									:inputUnits, "
						+ "									:outputItems, "
						+ "									:outputQuantities, "
						+ "									:outputUnits, "
						+ "									:weights, "		
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("processId", this.getId());
		modifyParameters.addValue("processName", this.getName());
		modifyParameters.addValue("fixedCost", this.getFixedCost());
		
		modifyParameters.addValue("inputItems", createArrayOfSQLType("varchar", this.getInputInventoryItemCodes().toArray()));
		modifyParameters.addValue("inputQuantities", createArrayOfSQLType("numeric", this.inputQuantities.toArray()));
		modifyParameters.addValue("inputUnits", createArrayOfSQLType("integer", this.getInputUnitIds().toArray()));
		
		modifyParameters.addValue("outputItems", createArrayOfSQLType("varchar", this.getOutputInventoryItemCodes().toArray()));
		modifyParameters.addValue("outputQuantities", createArrayOfSQLType("numeric", this.outputQuantities.toArray()));
		modifyParameters.addValue("outputUnits", createArrayOfSQLType("integer", this.getOutputUnitIds().toArray()));
		modifyParameters.addValue("weights", createArrayOfSQLType("integer", this.getWeights().toArray()));
		
		Integer qryResult = super.modify();
		if (qryResult == -2) {
			throw new ProcessRunningException();
		}
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Process_disable\"(:processId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("processId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class ProcessMapper implements RowMapper<Object> {

		public Process mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Process process = null;
				int id = rs.getInt("processId");
				if (!rs.wasNull()) {
					process = new Process(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("processName"),
										rs.getBigDecimal("fixedCost"));
				}
				return process;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	public List<Object> getAllToRun() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Process_getAllToRun\"(:loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ObjectMapper());
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Process_get\"(:processId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("processId", this.getId());
		super.get(new ProcessMapper());
	}
	
	public List<Object> getProcessInputs(Integer processId) throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Process_getInputs\"(:processId, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processId", processId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ProcessIOMapper());
	}
	
	public List<Object> getProcessOutputs(Integer processId) throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Process_getOutputs\"(:processId, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processId", processId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ProcessIOMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Process sourceProcess = (Process) sourceObject;
		setId(sourceProcess.getId());
		setEntityTypeInstanceId(sourceProcess.getEntityTypeInstanceId());
		setName(sourceProcess.getName());
		setStringId(sourceProcess.getStringId());
		setFixedCost(sourceProcess.getFixedCost());
		setInputInventoryItems(sourceProcess.getInputInventoryItems());
		setInputInventoryItemCodes(sourceProcess.getInputInventoryItemCodes());		
		setInputQuantities(sourceProcess.getInputQuantities());
		setInputUnits(sourceProcess.getInputUnits());
		setInputUnitIds(sourceProcess.getInputUnitIds());
		setOutputInventoryItems(sourceProcess.getOutputInventoryItems());
		setOutputInventoryItemCodes(sourceProcess.getOutputInventoryItemCodes());		
		setOutputQuantities(sourceProcess.getOutputQuantities());
		setOutputUnits(sourceProcess.getOutputUnits());
		setOutputUnitIds(sourceProcess.getOutputUnitIds());
		setWeights(sourceProcess.getWeights());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}

	public BigDecimal getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(BigDecimal fixedCost) {
		this.fixedCost = fixedCost;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}

	public ArrayList<InventoryItem> getInputInventoryItems() {
		return inputInventoryItems;
	}

	public void setInputInventoryItems(ArrayList<InventoryItem> inputInventoryItems) {
		this.inputInventoryItems = inputInventoryItems;
	}

	public ArrayList<String> getInputInventoryItemCodes() {
		return inputInventoryItemCodes;
	}

	public void setInputInventoryItemCodes(ArrayList<String> inputInventoryItemCodes) {
		this.inputInventoryItemCodes = inputInventoryItemCodes;
	}

	public ArrayList<Unit> getInputUnits() {
		return inputUnits;
	}

	public void setInputUnits(ArrayList<Unit> inputUnits) {
		this.inputUnits = inputUnits;
	}

	public ArrayList<Integer> getInputUnitIds() {
		return inputUnitIds;
	}

	public void setInputUnitIds(ArrayList<Integer> inputUnitIds) {
		this.inputUnitIds = inputUnitIds;
	}

	public ArrayList<BigDecimal> getInputQuantities() {
		return inputQuantities;
	}

	public void setInputQuantities(ArrayList<BigDecimal> inputQuantities) {
		this.inputQuantities = inputQuantities;
	}

	public ArrayList<InventoryItem> getOutputInventoryItems() {
		return outputInventoryItems;
	}

	public void setOutputInventoryItems(ArrayList<InventoryItem> outputInventoryItems) {
		this.outputInventoryItems = outputInventoryItems;
	}

	public ArrayList<String> getOutputInventoryItemCodes() {
		return outputInventoryItemCodes;
	}

	public void setOutputInventoryItemCodes(ArrayList<String> outputInventoryItemCodes) {
		this.outputInventoryItemCodes = outputInventoryItemCodes;
	}

	public ArrayList<Unit> getOutputUnits() {
		return outputUnits;
	}

	public void setOutputUnits(ArrayList<Unit> outputUnits) {
		this.outputUnits = outputUnits;
	}

	public ArrayList<Integer> getOutputUnitIds() {
		return outputUnitIds;
	}

	public void setOutputUnitIds(ArrayList<Integer> outputUnitIds) {
		this.outputUnitIds = outputUnitIds;
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
