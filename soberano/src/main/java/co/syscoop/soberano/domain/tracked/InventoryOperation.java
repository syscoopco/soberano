package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class InventoryOperation extends BusinessActivityTrackedObject {
	
	private Warehouse from = new Warehouse();
	private Warehouse to = new Warehouse();
	private Worker worker = new Worker();
	private ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
	private ArrayList<String> inventoryItemCodes = null;
	private ArrayList<Unit> units = new ArrayList<Unit>();
	private ArrayList<Integer> unitIds = null;
	private ArrayList<BigDecimal> quantities = new ArrayList<BigDecimal>();
	
	private void fillInventoryItemIds() {
		inventoryItemCodes = new ArrayList<String>();
		for (InventoryItem inventoryItem : inventoryItems) {
			inventoryItemCodes.add(inventoryItem.getStringId());
		}
	}
	
	private void fillUnitIds() {
		unitIds = new ArrayList<Integer>();
		for (Unit unit : units) {
			unitIds.add(unit.getId());
		}
	}
	
	public InventoryOperation() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_InventoryOperation_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_InventoryOperation_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public InventoryOperation(Integer id) {
		this.setId(id);
	}
	
	public InventoryOperation(Integer from,
							Integer to,
							Integer worker,
							ArrayList<InventoryItem> inventoryItems,
							ArrayList<Unit> units,
							ArrayList<BigDecimal> quantities) {
		super.setOccurrenceTime(new Date());
		this.from.setId(from);
		this.to.setId(to);
		this.worker.setId(worker);
		this.inventoryItems = inventoryItems;
		fillInventoryItemIds();	
		this.units = units;
		fillUnitIds();	
		this.quantities = quantities;
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
		recordQuery = "SELECT soberano.\"fn_InventoryOperation_create\"(:from, "
				+ "											:to, "
				+ "											:worker, "
				+ "											:inventoryItems, "
				+ "											:units, "
				+ "											:quantities, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("from", this.from.getId());
		recordParameters.addValue("to", this.to.getId());
		recordParameters.addValue("worker", this.worker.getId());
		recordParameters.addValue("inventoryItems", createArrayOfSQLType("varchar", this.inventoryItemCodes.toArray()));
		recordParameters.addValue("units", createArrayOfSQLType("integer", this.unitIds.toArray()));
		recordParameters.addValue("quantities", createArrayOfSQLType("numeric", this.quantities.toArray()));
		
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_InventoryOperation_disable\"(:inventoryOperationId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("inventoryOperationId", this.getId());

		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public ArrayList<BigDecimal> getQuantities() {
		return quantities;
	}

	public void setQuantities(ArrayList<BigDecimal> quantities) {
		this.quantities = quantities;
	}

	public ArrayList<Integer> getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(ArrayList<Integer> unitIds) {
		this.unitIds = unitIds;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}
}
