package co.syscoop.soberano.initiators;

import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.domain.tracked.Worker;

public class BackupUserCheckInitiator implements Initiator, InitiatorExt  {

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			List<Object> responsibilities = new Worker().getCurrentUserResponsibilities();
			if (!responsibilities.contains("Auditor") &&
				!responsibilities.contains("System admin") &&
				!responsibilities.contains("Manager")) {
				((Button) comps[1].
							getParent().
							getParent().
							getParent().
							getParent().
								query("#wndContentPanel").
									getParent().
									getParent().
										query("south").
											query("hbox").
												query("#btnBackup")).setVisible(false);
				Messagebox.show(Labels.getLabel("message.security.BackupUserRightsRequired"), 
							  					Labels.getLabel("messageBoxTitle.Information"), 
												0, 
												Messagebox.EXCLAMATION);
			}
		} catch (Exception e) {
			Messagebox.show(Labels.getLabel("message.security.BackupUserRightsRequired"), 
						  					Labels.getLabel("messageBoxTitle.Information"), 
											0, 
											Messagebox.EXCLAMATION);
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
