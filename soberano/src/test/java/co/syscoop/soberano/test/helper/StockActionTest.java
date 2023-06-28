package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import co.syscoop.soberano.util.SpringUtility;

public class StockActionTest extends ActionTest {
	
	protected static Combobox cmbWarehouse;
	protected static Grid grd;
		
	protected static StockForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		boxDetails = boxDetailsAgent.as(Hbox.class);
		StockForm stockForm = new StockForm(desktop,
											boxDetailsAgent.query("#cmbWarehouse").as(Combobox.class),
											boxDetailsAgent.query("#grd").as(Grid.class));
		return stockForm;
	}
}
