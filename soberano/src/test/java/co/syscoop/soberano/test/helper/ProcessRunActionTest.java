package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.SpringUtility;

public class ProcessRunActionTest extends ActionTest {
	
	protected static Combobox cmbProcess;
	protected static Combobox cmbCostCenter;
	protected static Decimalbox decRuns;
	protected static Button btnRecord;
	protected static Grid grd;
		
	protected static ProcessRunForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#vboxDetails");
		vboxDetails = boxDetailsAgent.as(Vbox.class);
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															boxDetailsAgent.query("#cmbProcess").as(Combobox.class),
															boxDetailsAgent.query("#cmbCostCenter").as(Combobox.class),
															boxDetailsAgent.query("#decRuns").as(Decimalbox.class),																					
															boxDetailsAgent.query("#btnRecord").as(Button.class),
															boxDetailsAgent.query("#grd").as(Grid.class));
			return processRunForm;
	}
}
