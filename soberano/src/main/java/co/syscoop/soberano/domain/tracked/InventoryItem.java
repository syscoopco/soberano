package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;

public class InventoryItem extends TrackedObject {
	
	private BigDecimal minimumInventoryLevel = new BigDecimal(0.0);
	private Integer unit = 1;
	
	public InventoryItem() {}
	
	public InventoryItem(Integer id) {
		super(id);
	}
	
	public InventoryItem(Integer id, String name) {
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
	public Integer print() throws SQLException {return null;}

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
}
