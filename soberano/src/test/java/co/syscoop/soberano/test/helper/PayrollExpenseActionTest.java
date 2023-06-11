package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

public class PayrollExpenseActionTest extends ActionTest {
	
	protected static Datebox dateExpenseDate;
	protected static Combobox cmbWorker;
	protected static Decimalbox decAmount;
	protected static Combobox cmbCurrency;
	protected static Textbox txtReference;
	protected static Button btnRecord;
	protected static Grid grd;
		
	protected static PayrollExpenseForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		boxDetails = boxDetailsAgent.as(Hbox.class);
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop,
																		boxDetailsAgent.query("#dateExpenseDate").as(Datebox.class),
																		boxDetailsAgent.query("#cmbWorker").as(Combobox.class),
																		boxDetailsAgent.query("#decAmount").as(Decimalbox.class),
																		boxDetailsAgent.query("#cmbCurrency").as(Combobox.class),
																		boxDetailsAgent.query("#txtReference").as(Textbox.class),
																		boxDetailsAgent.query("#btnRecord").as(Button.class),
																		boxDetailsAgent.query("#grd").as(Grid.class));
		return payrollExpenseForm;
	}
}
