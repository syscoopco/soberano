package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Hbox;

import co.syscoop.soberano.util.SpringUtility;

public class CashRegisterOperationActionTest extends ActionTest {
	
	protected static Button btnmc3;
	protected static Button btnmc5;
	protected static Button btnmc8;
	protected static Decimalbox decBalancemc3;
	protected static Decimalbox decBalancemc5;
	protected static Decimalbox decBalancemc8;
	protected static Decimalbox decEnteredAmountmc3;
	protected static Decimalbox decEnteredAmountmc5;
	protected static Decimalbox decEnteredAmountmc8;
	protected static Textbox txtInputExpression;
	protected static Decimalbox decInput;
	protected static Button btnCalc;
	protected static Decimalbox decCounted;
	protected static Button btnDeposit;
	protected static Button btnWithdraw;
	protected static Button btnCount;
	protected static Grid grd;
		
	protected static CashRegisterOperationForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		boxDetails = boxDetailsAgent.as(Hbox.class);
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							boxDetailsAgent.query("#btnmc3").as(Button.class),
																							boxDetailsAgent.query("#btnmc5").as(Button.class),
																							boxDetailsAgent.query("#btnmc8").as(Button.class),
																							boxDetailsAgent.query("#decBalancemc3").as(Decimalbox.class),
																							boxDetailsAgent.query("#decBalancemc5").as(Decimalbox.class),
																							boxDetailsAgent.query("#decBalancemc8").as(Decimalbox.class),																					
																							boxDetailsAgent.query("#decEnteredAmountmc3").as(Decimalbox.class),
																							boxDetailsAgent.query("#decEnteredAmountmc5").as(Decimalbox.class),
																							boxDetailsAgent.query("#decEnteredAmountmc8").as(Decimalbox.class),
																							boxDetailsAgent.query("#txtInputExpression").as(Textbox.class),
																							boxDetailsAgent.query("#decInput").as(Decimalbox.class),
																							boxDetailsAgent.query("#btnCalc").as(Button.class),
																							boxDetailsAgent.query("#decCounted").as(Decimalbox.class),
																							boxDetailsAgent.query("#btnDeposit").as(Button.class),
																							boxDetailsAgent.query("#btnWithdraw").as(Button.class),																					
																							boxDetailsAgent.query("#btnCount").as(Button.class),
																							boxDetailsAgent.query("#grd").as(Grid.class));
					return cashRegisterOperationForm;
	}
}
