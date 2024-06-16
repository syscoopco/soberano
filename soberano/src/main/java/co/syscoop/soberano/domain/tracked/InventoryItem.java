package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.exception.SoberanoException;

public class InventoryItem extends TrackedObject {
	
	private BigDecimal minimumInventoryLevel = new BigDecimal(0.0);
	private Integer unit = 1;
	
	public InventoryItem() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_InventoryItem_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	public InventoryItem(Integer id) {
		super(id);
	}
	
	public InventoryItem(Integer id, String name) {
		super(id, name);
	}
	
	public InventoryItem(String id, String name) {
		super(id, name);
	}
	
	public InventoryItem(Integer id, 
					Integer entityTypeInstanceId, 
					String name) {
		super(id, entityTypeInstanceId, name);
	}
	
	public InventoryItem(Integer id, 
						Integer entityTypeInstanceId, 
						String name,
						BigDecimal minimumInventoryLevel,
						Integer unit) {
		super(id, entityTypeInstanceId, name);
		this.setMinimumInventoryLevel(minimumInventoryLevel);
		this.setUnit(unit);
	}

	@Override
	public void get() throws SQLException {}

	@Override
	public Integer print() throws SoberanoException {return null;}

	@Override
	protected void copyFrom(Object object) {}

	public BigDecimal getMinimumInventoryLevel() {
		return minimumInventoryLevel;
	}

	public void setMinimumInventoryLevel(BigDecimal minimumInventoryLevel) {
		this.minimumInventoryLevel = minimumInventoryLevel;
	}
	
	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}
}
