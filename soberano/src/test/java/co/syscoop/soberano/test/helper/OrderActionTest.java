package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.SpringUtility;

public class OrderActionTest extends ActionTest {
	
	protected static Textbox txtLabel;
	protected static Textbox txtCounters;
	protected static Textbox txtCustomer;
	protected static Combobox cmbItemToOrder;
	protected static Textbox txtSpecialInstructions;
	protected static Decimalbox decQuantity;
	protected static Combobox cmbUnit;
	protected static Button btnMake;
	protected static Intbox intDiscountTop;
	protected static Decimalbox decAmountTop;
	protected static Intbox intDiscountBottom;
	protected static Decimalbox decAmountBottom;
	protected static Div divOrderItems;
	protected static Textbox txtStage;
		
	protected static OrderForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		vboxDetails = boxDetailsAgent.as(Vbox.class);
		OrderForm orderForm = new OrderForm(desktop,
											boxDetailsAgent.query("#txtLabel").as(Textbox.class),
											boxDetailsAgent.query("#txtCounters").as(Textbox.class),
											boxDetailsAgent.query("#txtCustomer").as(Textbox.class),
											boxDetailsAgent.query("#cmbItemToOrder").as(Combobox.class),
											boxDetailsAgent.query("#txtSpecialInstructions").as(Textbox.class),
											boxDetailsAgent.query("#decQuantity").as(Decimalbox.class),																					
											boxDetailsAgent.query("#cmbUnit").as(Combobox.class),
											boxDetailsAgent.query("#btnMake").as(Button.class),
											boxDetailsAgent.query("#intDiscountTop").as(Intbox.class),
											boxDetailsAgent.query("#decAmountTop").as(Decimalbox.class),
											boxDetailsAgent.query("#intDiscountBottom").as(Intbox.class),
											boxDetailsAgent.query("#decAmountBottom").as(Decimalbox.class),
											boxDetailsAgent.query("#divOrderItems").as(Div.class),
											boxDetailsAgent.query("#txtStage").as(Textbox.class));
		return orderForm;
	}
}
