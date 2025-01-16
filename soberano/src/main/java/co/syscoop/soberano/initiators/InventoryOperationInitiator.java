package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Combobox;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.ui.ZKUtility;

public class InventoryOperationInitiator implements Initiator, InitiatorExt {
	
	private Integer warehouseId = 1;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Combobox cmbFromWarehouse = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbFromWarehouse");					
			ZKUtility.setValueWOValidation(cmbFromWarehouse, warehouseId);
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
			warehouseId = ZKUtility.getObjectIdFromURLQuery("id");
		}
		catch(Exception ex) {
			warehouseId = 0; 
			ExceptionTreatment.log(ex);
		}
	}
}
