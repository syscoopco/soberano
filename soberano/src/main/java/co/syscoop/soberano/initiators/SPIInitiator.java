package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.models.SPIGridModel;
import co.syscoop.soberano.util.ui.ZKUtility;

public class SPIInitiator implements Initiator, InitiatorExt {
	
	private String shiftDateStr = "";
	private Integer warehouseId = 0;
	private Integer acquirableMaterialId = 0;
	private Boolean withOpeningStock = false;
	private Boolean withStockOnClosure = false;
	private Boolean withChanges = false;
	private Boolean withSurplus = false;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Datebox dateShift = (Datebox) comps[0].getPreviousSibling().query("#center").query("datebox").query("#dateShift");
			Combobox cmbWarehouse = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbWarehouse");					
			Combobox cmbMaterial = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbMaterial");
			Checkbox chkWithOpeningStock = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkWithOpeningStock");
			Checkbox chkWithStockOnClosure = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkWithStockOnClosure");
			Checkbox chkWithChanges = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkWithChanges");
			Checkbox chkSurplus = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkSurplus");
			
			try {
				try {dateShift.setText(shiftDateStr);} catch(Exception ex) {};
				try {ZKUtility.setValueWOValidation(cmbWarehouse, warehouseId);} catch(Exception ex) {};
				try {ZKUtility.setValueWOValidation(cmbWarehouse, acquirableMaterialId);} catch(Exception ex) {};
				try {chkWithOpeningStock.setChecked(withOpeningStock);} catch(Exception ex) {};
				try {chkWithStockOnClosure.setChecked(withStockOnClosure);} catch(Exception ex) {};
				try {chkWithChanges.setChecked(withChanges);} catch(Exception ex) {};
				try {chkSurplus.setChecked(withSurplus);} catch(Exception ex) {};
			}
			catch(Exception ex) {
			}
			
			SPIGridModel spiGridModel = null;
			if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse and inventory item
				spiGridModel = new SPIGridModel(shiftDateStr, 
												((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
												((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked(),
												"");			
			}
			else if (cmbWarehouse.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse
				spiGridModel = new SPIGridModel(shiftDateStr, 
												((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
												0, 
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked(),
												"");	
			}
			else if (cmbMaterial.getSelectedItem() != null) {
				
				//re-render the grid with the selected inventory item
				spiGridModel = new SPIGridModel(shiftDateStr, 
												0,
												((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(), 
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked(),
												"");	
			}
			else {
				//re-render the grid with the whole spi
				spiGridModel = new SPIGridModel(shiftDateStr, 
												0, 
												0, 
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked(),
												"");
			}
			((Grid) cmbWarehouse.query("#incGrid").query("#grd")).setModel(spiGridModel);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			ex.fillInStackTrace();
		}		
	}
	
	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}
	
	@Override
	public void doFinally() throws Exception {		
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		try {
			try {shiftDateStr = ZKUtility.getObjectStrIdFromURLQuery("sd");} catch(Exception ex) {shiftDateStr = "";};
			try {warehouseId = ZKUtility.getObjectIdFromURLQuery("id");} catch(Exception ex) {warehouseId = 0;};			
			try {acquirableMaterialId = ZKUtility.getObjectIdFromURLQuery("item");} catch(Exception ex) {setAcquirableMaterialId(0);};
			try {withOpeningStock = ZKUtility.getBooleanParamFromURLQuery("wos");} catch(Exception ex) {withOpeningStock = false;};			
			try {withStockOnClosure = ZKUtility.getBooleanParamFromURLQuery("wsoc");} catch(Exception ex) {withStockOnClosure = false;};
			try {withChanges = ZKUtility.getBooleanParamFromURLQuery("wc");} catch(Exception ex) {withChanges = false;};
			try {withSurplus = ZKUtility.getBooleanParamFromURLQuery("ws");} catch(Exception ex) {withSurplus = false;};
		}
		catch(Exception ex) {
			shiftDateStr = "";
			warehouseId = 0;
			acquirableMaterialId = 0;
			withOpeningStock = false;
			withStockOnClosure = false;
			withChanges = false;
			withSurplus = false;
			ExceptionTreatment.log(ex);
		}
	}

	public Integer getAcquirableMaterialId() {
		return acquirableMaterialId;
	}

	public void setAcquirableMaterialId(Integer acquirableMaterialId) {
		this.acquirableMaterialId = acquirableMaterialId;
	}

	public Boolean getWithOpeningStock() {
		return withOpeningStock;
	}

	public void setWithOpeningStock(Boolean withOpeningStock) {
		this.withOpeningStock = withOpeningStock;
	}

	public Boolean getWithStockOnClosure() {
		return withStockOnClosure;
	}

	public void setWithStockOnClosure(Boolean withStockOnClosure) {
		this.withStockOnClosure = withStockOnClosure;
	}

	public Boolean getWithChanges() {
		return withChanges;
	}

	public void setWithChanges(Boolean withChanges) {
		this.withChanges = withChanges;
	}

	public Boolean getWithSurplus() {
		return withSurplus;
	}

	public void setWithSurplus(Boolean withSurplus) {
		this.withSurplus = withSurplus;
	}

	public String getShiftDateStr() {
		return shiftDateStr;
	}

	public void setShiftDateStr(String shiftDateStr) {
		this.shiftDateStr = shiftDateStr;
	}
}
