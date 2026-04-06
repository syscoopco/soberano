package co.syscoop.soberano.composers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class BackupButtonComposer extends SelectorComposer {
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private int backupDatabase(String backupFileFullPathName) throws InterruptedException, IOException {

        // Determine the pg_dump command based on the operating system
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String pgDumpCommand = (isWindows ? "pg_dump.exe" : "pg_dump");

        // Build the command list
        List<String> command = new ArrayList<>();
        command.add(pgDumpCommand);
        command.add("-h");
        command.add("127.0.0.1");
        command.add("--no-password");
        command.add("-U");
        command.add("tcptool_backup");
        command.add("-d");
        command.add("soberano");
        command.add("--no-unlogged-table-data");
        command.add("--column-inserts");
        command.add("--inserts");
        command.add("--quote-all-identifiers");
        command.add("--schema");
        command.add("soberano");
        command.add("--schema");
        command.add("metamodel");
        command.add("--format");
        command.add("tar");
        command.add("-f");
        command.add(backupFileFullPathName);

        // Make backup
        ProcessBuilder processBuilder = new ProcessBuilder(command);       
        Process process = processBuilder.start();
        return process.waitFor();
    }
	
	@Listen("onClick = button#btnBackup")
    public void btnBackup_onClick() {
		
		try {
			List<Object> responsibilities = new Worker().getCurrentUserResponsibilities();
			if (responsibilities.contains("Auditor") ||
				responsibilities.contains("System admin") ||
				responsibilities.contains("Manager")) {
				
				String backupFileFullPathName = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/backups/" + 
												"soberanodb.backup";
				Integer backupProcessExitCode = backupDatabase(backupFileFullPathName);
				if (backupProcessExitCode == 0) {
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
					Messagebox.show(Labels.getLabel("error.pageBackup.ErrorWhileBackingDatabaseUp" + ": Backup process exited with code " + backupProcessExitCode.toString()), 
		  					Labels.getLabel("messageBoxTitle.Error"), 
							0, 
							Messagebox.ERROR);
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