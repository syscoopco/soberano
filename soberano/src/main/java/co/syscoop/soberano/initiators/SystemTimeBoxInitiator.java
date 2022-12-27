package co.syscoop.soberano.initiators;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Label;

public class SystemTimeBoxInitiator implements Initiator, InitiatorExt {
	
	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		
		((Label) comps[0].query("#lblSystemTime")).setValue(
				new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
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
	}
}
