package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import co.syscoop.soberano.util.Mobile;

public class DesktopPageInitiator implements Initiator, IPageInitiator {

	@Override
	public void goToCorrectSite(Page arg0) {
		
		String qryString = Executions.getCurrent().getDesktop().getQueryString();
		qryString = qryString == null?"":qryString;
		
		//if this is a mobile device
		if (Mobile.isMobile()) {
			
			//if it is being accessed by firefox
			if (Mobile.getUserAgent().contains("Firefox")) {
				
				//jump to the firefox page mobile version
				Executions.sendRedirect("/m_" + arg0.getRequestPath().substring(1) + (qryString.length() > 0?"?" + qryString:""));
			}
			else {
				//jump to the safari page mobile version
				Executions.sendRedirect("/ms_" + arg0.getRequestPath().substring(1) + (qryString.length() > 0?"?" + qryString:""));
			}			
		}
	}

	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		goToCorrectSite(arg0);
	}
}
