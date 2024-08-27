package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.models.SPIGridModel;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class SPIInitiator implements Initiator, InitiatorExt {
	
	private Integer warehouseId = 0;
	private Integer acquirableMaterialId = 0;
	private Integer closureId = 0;
	private String shift = "";

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Textbox txtShift = (Textbox) comps[0].getPreviousSibling().query("#center").query("textbox").query("#txtShift");
			Combobox cmbWarehouse = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbWarehouse");					
			Combobox cmbMaterial = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbMaterial");
			Checkbox chkWithOpeningStock = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkWithOpeningStock");
			Checkbox chkWithStockOnClosure = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkWithStockOnClosure");
			Checkbox chkWithChanges = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkWithChanges");
			Checkbox chkSurplus = (Checkbox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#chkSurplus");
			
			try {
				txtShift.setText(shift);
				ZKUtilitity.setValueWOValidation(cmbWarehouse, warehouseId);
			}
			catch(Exception ex) {}			
			
			SPIGridModel spiGridModel = null;
			if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse and inventory item
				spiGridModel = new SPIGridModel(closureId, 
												((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
												((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked());			
			}
			else if (cmbWarehouse.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse
				spiGridModel = new SPIGridModel(closureId, 
												((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
												0, 
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked());	
			}
			else if (cmbMaterial.getSelectedItem() != null) {
				
				//re-render the grid with the selected inventory item
				spiGridModel = new SPIGridModel(closureId, 
												0,
												((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(), 
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked());	
			}
			else {
				//re-render the grid with the whole spi
				spiGridModel = new SPIGridModel(closureId, 
												0, 
												0, 
												chkWithOpeningStock.isChecked(),
												chkWithStockOnClosure.isChecked(),
												chkWithChanges.isChecked(),
												chkSurplus.isChecked());
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
			warehouseId = ZKUtilitity.getObjectIdFromURLQuery("id");
			closureId = ZKUtilitity.getObjectIdFromURLQuery("scid");
			setAcquirableMaterialId(ZKUtilitity.getObjectIdFromURLQuery("item"));
			setAcquirableMaterialId(ZKUtilitity.getObjectIdFromURLQuery("item"));
			setShift(ZKUtilitity.getObjectStrIdFromURLQuery("sh"));
		}
		catch(Exception ex) {
			warehouseId = 0; 
			closureId = 0;
			setAcquirableMaterialId(0);
			setShift(""); 
			ExceptionTreatment.log(ex);
		}
	}

	public Integer getAcquirableMaterialId() {
		return acquirableMaterialId;
	}

	public void setAcquirableMaterialId(Integer acquirableMaterialId) {
		this.acquirableMaterialId = acquirableMaterialId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
}
