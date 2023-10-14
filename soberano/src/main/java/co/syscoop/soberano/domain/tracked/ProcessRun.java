package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.ProcessIOMapper;
import co.syscoop.soberano.database.relational.QueryBigDecimalResultMapper;
import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
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
	private String currentStageName = "";
	
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
	
	public ProcessRun(String code,
						Integer process,
						Integer costCenter,
						ArrayList<InventoryItem> inputItems,
						ArrayList<BigDecimal> inputQuantities,
						ArrayList<Unit> inputUnits,
						ArrayList<InventoryItem> outputItems,
						ArrayList<BigDecimal> outputQuantities,
						ArrayList<Unit> outputUnits,
						ArrayList<Integer> weights) {
		
		super.setOccurrenceTime(new Date());
		super.setStringId(code);
		this.process.setId(process);
		this.costCenter.setId(costCenter);
		this.inputItems = inputItems;
		fillInputItemIds();	
		this.inputUnits = inputUnits;
		fillInputUnitIds();	
		this.inputQuantities = inputQuantities;
		this.outputItems = outputItems;
		fillOutputItemIds();	
		this.outputUnits = outputUnits;
		fillOutputUnitIds();	
		this.outputQuantities = outputQuantities;
		this.weights = weights;
	}
	
	public ProcessRun(Integer processRunId,
					ArrayList<InventoryItem> outputItems,
					ArrayList<BigDecimal> outputQuantities,
					ArrayList<Unit> outputUnits,
					ArrayList<Integer> weights) {

		super.setOccurrenceTime(new Date());
		super.setId(processRunId);
		this.outputItems = outputItems;
		fillOutputItemIds();	
		this.outputUnits = outputUnits;
		fillOutputUnitIds();	
		this.outputQuantities = outputQuantities;
		this.weights = weights;
	}
	
	public ProcessRun(Integer id,
					String code,
					Integer entityTypeInstanceId, 
					Integer process,
					Integer costCenter) {
		
		super.setId(id);
		super.setStringId(code);
		super.setEntityTypeInstanceId(entityTypeInstanceId);
		this.process.setId(process);
		this.costCenter.setId(costCenter);
	}
	
	public ProcessRun(Integer id,
			String code,
			Integer entityTypeInstanceId, 
			Integer process,
			String processName,
			Integer costCenter,
			String costCenterName,
			String currentStageName) {

		super.setId(id);
		super.setStringId(code);
		super.setEntityTypeInstanceId(entityTypeInstanceId);
		this.process.setId(process);
		this.process.setName(processName);
		this.costCenter.setId(costCenter);
		this.costCenter.setName(costCenterName);
		this.setCurrentStageName(currentStageName);
	}
	
	public final class ProcessRunMapper implements RowMapper<Object> {

		public ProcessRun mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				ProcessRun processRun = null;
				int id = rs.getInt("itemId");
				if (!rs.wasNull()) {
					processRun = new ProcessRun(id,
										rs.getString("itemCode"),
										rs.getInt("entityTypeInstanceId"),
										rs.getInt("processId"),
										rs.getString("processName"),
										rs.getInt("costCenterId"),
										rs.getString("costCenterName"),
										rs.getString("currentStageName"));
				}
				return processRun;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}

	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_ProcessRun_get\"(:itemId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("itemId", this.getId());
		super.get(new ProcessRunMapper());
	}
	
	public List<Object> getProcessRunInputs(Integer processRunId) throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRun_getInputs\"(:processId, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ProcessIOMapper());
	}
	
	public List<Object> getProcessRunOutputs(Integer processRunId) throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRun_getOutputs\"(:processId, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ProcessIOMapper());
	}

	@Override
	public Integer print() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		ProcessRun sourceProcessRun = (ProcessRun) sourceObject;
		setId(sourceProcessRun.getId());
		setStringId(sourceProcessRun.getStringId());
		setEntityTypeInstanceId(sourceProcessRun.getEntityTypeInstanceId());
		setPrinter(sourceProcessRun.getPrinter());
		setProcess(sourceProcessRun.getProcess());
		setCostCenter(sourceProcessRun.getCostCenter());
		setCurrentStageName(sourceProcessRun.getCurrentStageName());
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_ProcessRun_create\"(:itemCode, "
				+ "											:process, "
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
		recordParameters.addValue("itemCode", this.getStringId());
		recordParameters.addValue("process", this.process.getId());
		recordParameters.addValue("costCenter", this.costCenter.getId());
		recordParameters.addValue("inputItems", createArrayOfSQLType("varchar", this.inputItemCodes.toArray()));
		recordParameters.addValue("inputQuantities", createArrayOfSQLType("numeric", this.inputQuantities.toArray()));
		recordParameters.addValue("inputUnits", createArrayOfSQLType("integer", this.inputUnitIds.toArray()));
		recordParameters.addValue("outputItems", createArrayOfSQLType("varchar", this.outputItemCodes.toArray()));
		recordParameters.addValue("outputQuantities", createArrayOfSQLType("numeric", this.outputQuantities.toArray()));
		recordParameters.addValue("outputUnits", createArrayOfSQLType("integer", this.outputUnitIds.toArray()));
		recordParameters.addValue("weights", createArrayOfSQLType("integer", this.weights.toArray()));
		return super.record();
	}
	
	public Integer close() throws SQLException, Exception {
	
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_ProcessRun_close\"(:itemId, "
				+ "											:outputItems, "
				+ "											:outputQuantities, "
				+ "											:outputUnits, "
				+ "											:weights, "
				+ "											:loginname) AS queryresult";
		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("itemId", this.getId());
		parametersMap.put("outputItems", createArrayOfSQLType("varchar", this.outputItemCodes.toArray()));
		parametersMap.put("outputQuantities", createArrayOfSQLType("numeric", this.outputQuantities.toArray()));
		parametersMap.put("outputUnits", createArrayOfSQLType("integer", this.outputUnitIds.toArray()));
		parametersMap.put("weights", createArrayOfSQLType("integer", this.weights.toArray()));
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer cancel() throws SQLException, Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRun_cancel\"(:processRunId, :loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processRunId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public List<Object> getProcessInputs(Integer processRunId) throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRun_getInputs\"(:processRunId, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ProcessIOMapper());
	}
	
	public List<Object> getProcessOutputs(Integer processRunId) throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProcessRun_getOutputs\"(:processRunId, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new ProcessIOMapper());
	}
	
	public BigDecimal estimateCost(Integer processId, Integer costCenterId) throws SQLException, Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Process_estimateCost\"(:processId, :costCenterId, :loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("processId", processId);
		parametersMap.put("costCenterId", costCenterId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (BigDecimal) super.query(qryStr, parametersMap, new QueryBigDecimalResultMapper()).get(0);
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

	public String getCurrentStageName() {
		return currentStageName;
	}

	public void setCurrentStageName(String currentStageName) {
		this.currentStageName = currentStageName;
	}
}
