package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Window;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.ui.ZKUtility;

public class OrderInitiator implements Initiator, InitiatorExt {
	
	private Integer orderId = 1;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			OrderFormHelper form = new OrderFormHelper();
			form.initFormForManagement((Window) comps[1].getParent().getParent().getParent().getParent().query("#wndContentPanel"), orderId);
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
			orderId = ZKUtility.getObjectIdFromURLQuery("id");
		}
		catch(Exception ex) {
			orderId = 0; 
			ExceptionTreatment.log(ex);
		}
	}
}
