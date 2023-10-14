package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Window;

import co.syscoop.soberano.ui.helper.ProcessRunFormHelper;
import co.syscoop.soberano.util.ZKUtilitity;

public class ProcessRunInitiator implements Initiator, InitiatorExt {
	
	Integer processRunId = 1;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			ProcessRunFormHelper form = new ProcessRunFormHelper();
			form.initForm((Window) comps[1].getParent().getParent().getParent().getParent().query("#wndContentPanel"), processRunId);
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
			processRunId = Integer.parseInt(ZKUtilitity.parseURLQueryStringForParam("id"));
		}
		catch(Exception ex) {
			processRunId = 0; 
		}
	}
}
