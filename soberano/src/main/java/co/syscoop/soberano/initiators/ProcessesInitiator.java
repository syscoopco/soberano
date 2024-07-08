package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Include;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.ui.helper.ProcessFormHelper;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class ProcessesInitiator implements Initiator, InitiatorExt {
	
	private Integer processId = 1;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			ProcessFormHelper form = new ProcessFormHelper();
			form.initForm((Include) comps[1].getParent().getParent().getParent().getParent().query("#incDetails"), processId);
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
			processId = ZKUtilitity.getObjectIdFromURLQuery("id");
		}
		catch(Exception ex) {
			processId = 0; 
			ExceptionTreatment.log(ex);
		}
	}
}
