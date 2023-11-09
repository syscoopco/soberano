package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.SpringUtility;

public class NewOrderActionTest extends ActionTest {
	
	protected static Textbox txtLabel;
	protected static Combobox cmbCustomer;
	protected static Grid grdCounters;
	protected static Button btnRecord;
	protected static Grid grd;
		
	protected static NewOrderForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		vboxDetails = boxDetailsAgent.as(Vbox.class);
		NewOrderForm newOrderForm = new NewOrderForm(desktop,
													boxDetailsAgent.query("#txtLabel").as(Textbox.class),
													boxDetailsAgent.query("#cmbCustomer").as(Combobox.class),
													boxDetailsAgent.query("#grdCounters").as(Grid.class),																					
													boxDetailsAgent.query("#btnRecord").as(Button.class),
													boxDetailsAgent.query("#grd").as(Grid.class));
			return newOrderForm;
	}
}
