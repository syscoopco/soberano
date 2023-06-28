package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;

public class StockForm extends ConstrainedForm {
	
	private Combobox cmbWarehouse;
	private Grid grd;

	public StockForm(DesktopAgent desktop,
					Combobox cmbWarehouse,
					Grid grd) {
		
		this.setDesktop(desktop);
		this.setCmbWarehouse(cmbWarehouse);
		this.grd = grd;
	}
	
	public Grid getGrd() {
		return grd;
	}

	public void setGrd(Grid grd) {
		this.grd = grd;
	}

	public Combobox getCmbWarehouse() {
		return cmbWarehouse;
	}

	public void setCmbWarehouse(Combobox cmbWarehouse) {
		this.cmbWarehouse = cmbWarehouse;
	}
}
