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
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.CashRegisterOperationActionTest;
import co.syscoop.soberano.test.helper.CashRegisterOperationForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(8)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O8_CashRegisterOperationTest_record extends CashRegisterOperationActionTest {
	
	protected CashRegisterOperationForm cashRegisterOperationForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
																//to runtime locale not available under 
																//testing environment
		
		//Zats.init("./src/main/webapp");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Zats.cleanup();
		//Zats.end();
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
			
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
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
			
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
	@Order(4)
	final void testCase4() {

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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
			
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
	@Order(6)
	final void testCase6() {

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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
			
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
			
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
	@Order(12)
	final void testCase12() {

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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
			
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
	@Order(14)
	final void testCase14() {

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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
			
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnWithdrawButton(desktop);
			clickOnWithdrawButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
			
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnDepositButton(desktop);
			clickOnDepositButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	private void checkBalances(Double mc3ExpectedBalance,
								Double mc5ExpectedBalance,
								Double mc8ExpectedBalance) {
		
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
																							desktop.query("grid").query("#grd").as(Grid.class));
		assertEquals(new BigDecimal(mc3ExpectedBalance).subtract(cashRegisterOperationForm.getDecBalancemc3().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc3 balance");
		assertEquals(new BigDecimal(mc5ExpectedBalance).subtract(cashRegisterOperationForm.getDecBalancemc5().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc5 balance");
		assertEquals(new BigDecimal(mc8ExpectedBalance).subtract(cashRegisterOperationForm.getDecBalancemc8().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong mc8 balance");
	}
	
	@Test
	@Order(19)
	final void testCase19() {	
		try {
			checkBalances(19952.0241, 45911.51952, 108642.469);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(20)
	final void testCase20() {

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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnCountButton(desktop);
			clickOnCountButton(desktop);
			
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
	@Order(21)
	final void testCase21() {

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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnCountButton(desktop);
			clickOnCountButton(desktop);
			
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
	@Order(22)
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(100.123));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnCountButton(desktop);
			clickOnCountButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(23)
	final void testCase23() {	
		try {
			checkBalances(100.123, 0.0, 0.0);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(24)
	final void testCase24() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(-50.3451));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(1000.678));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(0));
			btnCalc.click();
			
			clickOnCountButton(desktop);
			clickOnCountButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(25)
	final void testCase25() {	
		try {
			checkBalances(-50.3451, 1000.678, 0.0);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(26)
	final void testCase26() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
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
																							desktop.query("grid").query("#grd").as(Grid.class));	
		try {
			ComponentAgent btnmc3 = desktop.query("#wndContentPanel").query("#btnmc3");
			btnmc3.click();			
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(10000.901));
			ComponentAgent btnCalc = desktop.query("#wndContentPanel").query("#btnCalc");
			btnCalc.click();
			
			ComponentAgent btnmc5 = desktop.query("#wndContentPanel").query("#btnmc5");
			btnmc5.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(23456.09876));
			btnCalc.click();
			
			ComponentAgent btnmc8 = desktop.query("#wndContentPanel").query("#btnmc8");
			btnmc8.click();
			cashRegisterOperationForm.setComponentValue(cashRegisterOperationForm.getDecInput(), new BigDecimal(54321.2345));
			btnCalc.click();
			
			clickOnCountButton(desktop);
			clickOnCountButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(27)
	final void testCase27() {	
		try {
			checkBalances(10000.901, 23456.09876, 54321.2345);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}