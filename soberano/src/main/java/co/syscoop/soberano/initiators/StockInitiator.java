package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.models.StockGridModel;
import co.syscoop.soberano.util.ui.ZKUtility;

public class StockInitiator implements Initiator, InitiatorExt {
	
	private Integer warehouseId = 0;
	private Integer acquirableMaterialId = 0;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Combobox cmbWarehouse = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbWarehouse");					
			Combobox cmbMaterial = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbMaterial");
			try {ZKUtility.setValueWOValidation(cmbWarehouse, warehouseId);} catch(Exception ex) {};
			try {ZKUtility.setValueWOValidation(cmbMaterial, acquirableMaterialId);} catch(Exception ex) {};
			
			StockGridModel stockGridModel = null;
			if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse and inventory item
				stockGridModel = new StockGridModel(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
												((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
												"");			
			}
			else if (cmbWarehouse.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse
				stockGridModel = new StockGridModel(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
												0, 
												"");	
			}
			else if (cmbMaterial.getSelectedItem() != null) {
				
				//re-render the grid with the selected inventory item
				stockGridModel = new StockGridModel(0,
												((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(), 
												"");	
			}
			else {
				//re-render the grid with the whole stock records
				stockGridModel = new StockGridModel(0, 
												0, 
												"");
			}
			((Grid) cmbWarehouse.query("#incGrid").query("#grd")).setModel(stockGridModel);
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
			try {warehouseId = ZKUtility.getObjectIdFromURLQuery("id");} catch(Exception ex) {warehouseId = 0;};			
			try {acquirableMaterialId = ZKUtility.getObjectIdFromURLQuery("item");} catch(Exception ex) {setAcquirableMaterialId(0);};
		}
		catch(Exception ex) {
			warehouseId = 0; 
			ExceptionTreatment.log(ex);
		}
	}

	public Integer getAcquirableMaterialId() {
		return acquirableMaterialId;
	}

	public void setAcquirableMaterialId(Integer acquirableMaterialId) {
		this.acquirableMaterialId = acquirableMaterialId;
	}
}
