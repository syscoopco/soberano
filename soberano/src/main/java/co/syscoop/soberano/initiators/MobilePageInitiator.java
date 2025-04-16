package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import co.syscoop.soberano.util.Mobile;

public class MobilePageInitiator implements Initiator, IPageInitiator {

	@Override
	public void goToCorrectSite(Page arg0) {
		
		String qryString = Executions.getCurrent().getDesktop().getQueryString();
		qryString = qryString == null?"":qryString;
		
		//if this is not a mobile device
		if (!Mobile.isMobile()) {
			
			//jump to the page full site version
			if (arg0.getRequestPath().contains("/m_")) {
				Executions.sendRedirect("/" + arg0.getRequestPath().substring(5) + (qryString.length() > 0?"?" + qryString:""));
			}
			else {
				Executions.sendRedirect("/" + arg0.getRequestPath().substring(6) + (qryString.length() > 0?"?" + qryString:""));
			}
		}
		else {
			
			//if it is being accessed by firefox
			if (Mobile.getUserAgent().contains("Firefox")) {
				
				//jump to the firefox page mobile version
				if (arg0.getRequestPath().substring(1).contains("/ms_")) {
					Executions.sendRedirect(arg0.getRequestPath().substring(1).replace("/ms_", "/m_") + (qryString.length() > 0?"?" + qryString:""));
				}
			}
			else {
				
				//jump to the safari page mobile version
				if (arg0.getRequestPath().substring(1).contains("m/m_")) {
					Executions.sendRedirect(arg0.getRequestPath().substring(1).replace("/m_", "/ms_") + (qryString.length() > 0?"?" + qryString:""));
				}
			}
		}
	}

	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		goToCorrectSite(arg0);
	}
}