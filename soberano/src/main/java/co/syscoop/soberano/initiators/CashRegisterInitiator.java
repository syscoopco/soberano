package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Window;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.ui.helper.CashRegisterFormHelper;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class CashRegisterInitiator implements Initiator, InitiatorExt {
	
	private Integer cashRegisterId = 1;
	private Integer orderId = 0;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			CashRegisterFormHelper form = new CashRegisterFormHelper();
			form.initForm((Window) comps[1].getParent().getParent().getParent().getParent().query("#wndContentPanel"), cashRegisterId, orderId);
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
			if (ZKUtilitity.splitQuery().get("crid") == null) {
				cashRegisterId = 1;
			}
			else {
				cashRegisterId = Integer.parseInt(ZKUtilitity.splitQuery().get("crid").get(0));
			}
			orderId = Integer.parseInt(ZKUtilitity.splitQuery().get("oid").get(0));
		}
		catch(Exception ex) {
			cashRegisterId = 1; 
			orderId = null; 
			ExceptionTreatment.log(ex);
		}
	}
}
