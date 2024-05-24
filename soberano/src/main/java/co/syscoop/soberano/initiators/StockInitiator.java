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
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class StockInitiator implements Initiator, InitiatorExt {
	
	Integer warehouseId = 1;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Combobox cmbWarehouse = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbWarehouse");					
			ZKUtilitity.setValueWOValidation(cmbWarehouse, warehouseId);
			
			StockGridModel stockGridModel = null;
			if (cmbWarehouse.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse stock
				stockGridModel = new StockGridModel(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());			
			}
			else {
				//re-render the grid with the whole stock
				stockGridModel = new StockGridModel();	
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
			if (ZKUtilitity.splitQuery().get("id") == null) {
				warehouseId = 0; 
			}
			else {
				warehouseId = Integer.parseInt(ZKUtilitity.splitQuery().get("id").get(0));
			}
		}
		catch(Exception ex) {
			warehouseId = 0; 
			ExceptionTreatment.log(ex);
		}
	}
}
