package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.util.SpringUtility;

public class PrintTicketActionTest extends ActionTest {
	
	protected static Intbox intOrderNumber;
	protected static Button btnRetrieve;
	protected static Button txtReport;
	protected static Button btnReopen;
		
	protected static PrintTicketForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		boxDetails = boxDetailsAgent.as(Hbox.class);
		PrintTicketForm printTicketForm = new PrintTicketForm(desktop,
																boxDetailsAgent.query("#intOrderNumber").as(Intbox.class),
																boxDetailsAgent.query("#btnRetrieve").as(Button.class),
																boxDetailsAgent.query("#txtReport").as(Textbox.class),
																boxDetailsAgent.query("#btnReopen").as(Button.class));
		return printTicketForm;
	}
}
