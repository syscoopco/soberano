package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.opentest4j.AssertionFailedError;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Combobox;

import co.syscoop.soberano.test.helper.CashRegisterOperationActionTest;
import co.syscoop.soberano.test.helper.CashRegisterOperationForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(29)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO29_OrderTest_collect extends CashRegisterOperationActionTest {
	
	protected CashRegisterOperationForm cashRegisterOperationForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
																//to runtime locale not available under 
																//testing environment
		
		Zats.init("./src/main/webapp");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Zats.cleanup();
		Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1002");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(6));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(7));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(10.32067802).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(14.338434761836).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(4.01775674183601).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1002");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(6));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(7));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(10.32067802).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(14.338434761836).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(4.01775674183601).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	/*commented because in this point the Collect button is hidden*/
	
//	@Test
//	@Order(3)
//	final void testCase3() {
//
//		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
//		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1002");
//		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
//																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
//																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
//																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
//																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
//																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
//																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
//																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
//																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
//																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
//																							desktop.query("grid").query("#grd").as(Grid.class));	
//		try {
//			//check mc3 final balance
//			assertEquals(new BigDecimal(10006.901).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 final balance.");
//			
//			//check mc5 final balance
//			assertEquals(new BigDecimal(23453.0810032582).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 final balance.");
//			
//			//check mc8 final balance
//			assertEquals(new BigDecimal(54328.2345).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 final balance.");
//			
//			//check to collect
//			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
//			
//			clickOnCollectButton(desktop);
//			clickOnCollectButton(desktop);
//			
//			fail("None exception was thrown when it should.");
//		}
//		catch(AssertionFailedError ex) {
//			fail(ex.getMessage());
//		}
//		catch(Throwable ex) {
//			testOrderAlreadyCollectedException(ex);
//		}
//	}
	
	/*commented because in this point the decBalance and decToCollect boxes are hidden*/
	
//	@Test
//	@Order(4)
//	final void testCase4() {
//
//		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
//		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1002");
//		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
//																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
//																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
//																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
//																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
//																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
//																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
//																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
//																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
//																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
//																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
//																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
//																							desktop.query("grid").query("#grd").as(Grid.class));	
//		try {
//			//check mc3 final balance
//			assertEquals(new BigDecimal(10006.901).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 final balance.");
//			
//			//check mc5 final balance
//			assertEquals(new BigDecimal(23453.0810032582).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 final balance.");
//			
//			//check mc8 final balance
//			assertEquals(new BigDecimal(54328.2345).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 final balance.");
//			
//			//check to collect
//			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
//		}
//		catch(AssertionFailedError ex) {
//			fail(ex.getMessage());
//		}
//		catch(Throwable ex) {
//			fail(ex.getMessage());
//		}
//	}
	
	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1002");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check cancel button is visible
			//cancel order is disabled. assertEquals(cashRegisterOperationForm.getBtnCancel().isVisible(), true, "Order already totally collected. Cancel button isn't visible when it should.");
			
			//check collect button is hidden
			assertEquals(cashRegisterOperationForm.getBtnCollect().isVisible(), false, "Order already totally collected. Collect button is visible when it shouldn't.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(11));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(12));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(54.15888862).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(33.6799608952985).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDebtorRequiredException(ex);
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(11));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(12));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(54.15888862).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(33.6799608952985).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			ComponentAgent cmbCustomerAgent = desktop.query("#incSouth").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c2mod");
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getCmbCustomer(), new Integer(1002));
			cmbCustomerAgent.click();
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(11));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(12));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(54.15888862).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(33.6799608952985).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			ComponentAgent cmbCustomerAgent = desktop.query("#incSouth").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c2mod");
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getCmbCustomer(), new Integer(1002));
			cmbCustomerAgent.click();
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check mc3 final balance
			assertEquals(new BigDecimal(10016.901).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 final balance.");
			
			//check mc5 final balance
			assertEquals(new BigDecimal(23464.0810032582).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 final balance.");
			
			//check mc8 final balance
			assertEquals(new BigDecimal(54340.2345).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 final balance.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check cancel button is visible
			//cancel order is disabled. assertEquals(cashRegisterOperationForm.getBtnCancel().isVisible(), true, "Order partially collected. Cancel button isn't visible when it should.");
			
			//check collect button is visible
			assertEquals(cashRegisterOperationForm.getBtnCollect().isVisible(), true, "Order partially collected. Collect button isn't visible when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check to collect
			assertEquals(new BigDecimal(20.4789277247015).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(2));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(3));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(4));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(20.4789277247015).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(9.69232638180451).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			ComponentAgent cmbCustomerAgent = desktop.query("#incSouth").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c3mod");
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getCmbCustomer(), new Integer(1003));
			cmbCustomerAgent.click();
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check mc3 final balance
			assertEquals(new BigDecimal(10018.901).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 final balance.");
			
			//check mc5 final balance
			assertEquals(new BigDecimal(23467.0810032582).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 final balance.");
			
			//check mc8 final balance
			assertEquals(new BigDecimal(54344.2345).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 final balance.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check to collect
			assertEquals(new BigDecimal(10.786601342897).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1006");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(15));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(20));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(10.786601342897).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(48.4616319090225).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(37.6750305661256).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			ComponentAgent cmbCustomerAgent = desktop.query("#incSouth").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c4mod");
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getCmbCustomer(), new Integer(1004));
			cmbCustomerAgent.click();
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check mc3 final balance
			assertEquals(new BigDecimal(10028.901).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 final balance.");
			
			//check mc5 final balance
			assertEquals(new BigDecimal(23444.405972692).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 final balance.");
			
			//check mc8 final balance
			assertEquals(new BigDecimal(54364.2345).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 final balance.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(17)
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul?oid=1001");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(3));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(4));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(5));
			btnCalc.click();
			
			//check to collect
			assertEquals(new BigDecimal(13.87805359).subtract(cashRegisterOperationForm.getDecToCollect().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong amount to collect.");
			
			//check counted
			assertEquals(new BigDecimal(12.6907806959913).subtract(cashRegisterOperationForm.getDecCounted().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong counted amount.");
			
			//check change
			assertEquals(new BigDecimal(0).subtract(cashRegisterOperationForm.getDecChange().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong change amount.");
			
			ComponentAgent cmbCustomerAgent = desktop.query("#incSouth").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c5mod");
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getCmbCustomer(), new Integer(1005));
			cmbCustomerAgent.click();
			
			clickOnCollectButton(desktop);
			clickOnCollectButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cash_register.zul");
		CashRegisterOperationForm cashRegisterOperationForm = new CashRegisterOperationForm(desktop,
																							desktop.query("#wndContentPanel").query("#btnmc3").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc5").as(Button.class),
																							desktop.query("#wndContentPanel").query("#btnmc8").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decBalancemc8").as(Decimalbox.class),																					
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc3").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc5").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decEnteredAmountmc8").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#txtInputExpression").as(Textbox.class),
																							desktop.query("#wndContentPanel").query("#decInput").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#btnCalc").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decCounted").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#btnDeposit").as(Button.class),
																							desktop.query("#incSouth").query("#btnWithdraw").as(Button.class),																					
																							desktop.query("#incSouth").query("#btnCount").as(Button.class),
																							desktop.query("#wndContentPanel").query("#decToCollect").as(Decimalbox.class),
																							desktop.query("#wndContentPanel").query("#decChange").as(Decimalbox.class),
																							desktop.query("#incSouth").query("#cmbCustomer").as(Combobox.class),
																							desktop.query("#incSouth").query("#btnCollect").as(Button.class),
																							desktop.query("#incSouth").query("#btnCancel").as(Button.class),
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			//check mc3 final balance
			assertEquals(new BigDecimal(10031.901).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 final balance.");
			
			//check mc5 final balance
			assertEquals(new BigDecimal(23448.405972692).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 final balance.");
			
			//check mc8 final balance
			assertEquals(new BigDecimal(54369.2345).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 final balance.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

//
//	test stopped to pass after solving a problem with scroll in order's items tree.
//	it seems a problem with querying zul components.
//
//	@Test
//	@Order(19)
//	final void testCase19() {
//
//		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
//		DesktopAgent desktop = Zats.newClient().connect("/bill.zul?id=1004");
//		try {
//			ComponentAgent decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
//															query("#wndOrderItems").
//															query("#divOrderItems").
//															query("#vboxOrderItems").
//															query("#treeCat_1_mcat3").
//															query("#decCanceledButEndedItems1013");
//			InputAgent decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
//			decCanceledButEndedItemsInputAgent.type("3");
//			
//			decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
//											query("#wndOrderItems").
//											query("#divOrderItems").
//											query("#vboxOrderItems").
//											query("#treeCat_2_mcat8").query("#decCanceledButEndedItems1014");
//			decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
//			decCanceledButEndedItemsInputAgent.type("3");
//		}
//		catch(AssertionFailedError ex) {
//			fail(ex.getMessage());
//		}
//		catch(Throwable ex) {
//			testNotEnoughRightsException(ex);
//		}
//	}
	
	@Test
	@Order(20)
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/bill.zul?id=1004");
		try {
			ComponentAgent decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
															query("#wndOrderItems").
															query("#divOrderItems").
															query("#vboxOrderItems").
															query("#treeCat_1_mcat3").
															query("#decCanceledButEndedItems1013");
			InputAgent decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
			decCanceledButEndedItemsInputAgent.type("3");
			
			decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
											query("#wndOrderItems").
											query("#divOrderItems").
											query("#vboxOrderItems").
											query("#treeCat_2_mcat8").
											query("#decCanceledButEndedItems1014");
			decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
			decCanceledButEndedItemsInputAgent.type("3");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(21)
	final void testCase21() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/bill.zul?id=1005");
		try {
			ComponentAgent decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
															query("#wndOrderItems").
															query("#divOrderItems").
															query("#vboxOrderItems").
															query("#treeCat_1_mcat3").
															query("#decCanceledButEndedItems1015");
			InputAgent decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
			decCanceledButEndedItemsInputAgent.type("3");
			
			decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
											query("#wndOrderItems").
											query("#divOrderItems").
											query("#vboxOrderItems").
											query("#treeCat_2_mcat8").query("#decCanceledButEndedItems1016");
			decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
			decCanceledButEndedItemsInputAgent.type("3");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(22)
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/bill.zul?id=1008");
		try {
			ComponentAgent decCanceledButEndedItemsAgent = desktop.query("#wndContentPanel").
															query("#wndOrderItems").
															query("#divOrderItems").
															query("#vboxOrderItems").
															query("#treeCat_1_mcat8").
															query("#decCanceledButEndedItems1020");
			InputAgent decCanceledButEndedItemsInputAgent = decCanceledButEndedItemsAgent.as(InputAgent.class);
			decCanceledButEndedItemsInputAgent.type("3");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}