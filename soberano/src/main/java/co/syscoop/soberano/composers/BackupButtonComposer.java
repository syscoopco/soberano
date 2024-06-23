package co.syscoop.soberano.composers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Worker;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class BackupButtonComposer extends SelectorComposer {
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnBackup")
    public void btnBackup_onClick() {
		
		try {
			List<Object> responsibilities = new Worker().getCurrentUserResponsibilities();
			if (responsibilities.contains("Auditor") ||
				responsibilities.contains("System admin") ||
				responsibilities.contains("Manager")) {
				InputStream is = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream("/records/backups/soberanodb.backup");
				if (is != null) {
					Filedownload.save(is, "text/html", "soberanodb.backup");
				}
				else {
					Messagebox.show(Labels.getLabel("message.pageBackup.BackupNotFound"), 
								  					Labels.getLabel("messageBoxTitle.Information"), 
													0, 
													Messagebox.EXCLAMATION);		
				}
			}
			else {
				Messagebox.show(Labels.getLabel("message.security.BackupUserRightsRequired"), 
							  					Labels.getLabel("messageBoxTitle.Information"), 
												0, 
												Messagebox.EXCLAMATION);
			}
		}
        catch(Exception e) {
        	Messagebox.show(Labels.getLabel("error.pageBackup.ErrorWhileBackingDatabaseUp"), 
						  					Labels.getLabel("messageBoxTitle.Error"), 
											0, 
											Messagebox.ERROR);
        }
    }
}