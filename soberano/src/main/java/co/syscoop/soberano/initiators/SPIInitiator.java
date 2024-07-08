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
import co.syscoop.soberano.models.SPIGridModel;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class SPIInitiator implements Initiator, InitiatorExt {
	
	private Integer warehouseId = 0;
	private Integer closureId = 0;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Combobox cmbWarehouse = (Combobox) comps[0].getPreviousSibling().query("#center").query("combobox").query("#cmbWarehouse");					
			
			try {
				ZKUtilitity.setValueWOValidation(cmbWarehouse, warehouseId);
			}
			catch(Exception ex) {}			
			
			SPIGridModel spiGridModel = null;
			if (cmbWarehouse.getSelectedItem() != null) {
				
				//re-render the grid with the selected warehouse spi
				spiGridModel = new SPIGridModel(closureId, ((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());			
			}
			else {
				//re-render the grid with the whole spi
				spiGridModel = new SPIGridModel(closureId, 0);	
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
		}
		catch(Exception ex) {
			warehouseId = 0; 
			closureId = 0;
			ExceptionTreatment.log(ex);
		}
	}
}
