package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.SpringUtility;

public class InventoryOperationActionTest extends ActionTest {
	
	protected static Combobox cmbFromWarehouse;
	protected static Combobox cmbToWarehouse;
	protected static Combobox cmbWorker;
	protected static Combobox cmbMaterial;
	protected static Decimalbox decQuantity;
	protected static Combobox cmbUnit;
	protected static Button btnRecord;
	protected static Grid grd;
		
	protected static InventoryOperationForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#vboxDetails");
		vboxDetails = boxDetailsAgent.as(Vbox.class);
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					boxDetailsAgent.query("#cmbFromWarehouse").as(Combobox.class),
																					boxDetailsAgent.query("#cmbToWarehouse").as(Combobox.class),
																					boxDetailsAgent.query("#cmbWorker").as(Combobox.class),
																					boxDetailsAgent.query("#cmbMaterial").as(Combobox.class),
																					boxDetailsAgent.query("#decQuantity").as(Decimalbox.class),
																					boxDetailsAgent.query("#cmbUnit").as(Combobox.class),																					
																					boxDetailsAgent.query("#btnRecord").as(Button.class),
																					boxDetailsAgent.query("#grd").as(Grid.class));
					return inventoryOperationForm;
	}
}
