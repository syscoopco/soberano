package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import co.syscoop.soberano.domain.untracked.AcquisitionExpense;

public class MaterialAcquisition {
	
	private AcquisitionExpense expense = new AcquisitionExpense();	
	private BigDecimal quantity = new BigDecimal(0.0);	
	private AcquirableMaterial material = new AcquirableMaterial();	
	private Unit unit = new Unit();

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public AcquirableMaterial getMaterial() {
		return material;
	}

	public void setMaterial(AcquirableMaterial material) {
		this.material = material;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public AcquisitionExpense getExpense() {
		return expense;
	}

	public void setExpense(AcquisitionExpense expense) {
		this.expense = expense;
	}
}
