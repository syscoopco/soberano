package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.StringIdCodeGenerator;

public class TxtCodeRandomValueInitiator implements Initiator, InitiatorExt {
	
	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		
		try{
			Textbox txtCode;
			StringIdCodeGenerator sidcodeg = new StringIdCodeGenerator();
			try {
				txtCode = (Textbox) comps[2].query("#incDetails").query("#txtCode");
			}
			catch(Exception ex) {
				txtCode = (Textbox) comps[2].query("#boxDetails").query("#txtCode");
			}
			txtCode.setValue((sidcodeg.getTenCharsRandomString("")));
		}
		catch(Exception ex) {
			return;
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
	}
}