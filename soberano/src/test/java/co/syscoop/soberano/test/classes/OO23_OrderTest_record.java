package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

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
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.test.helper.NewOrderActionTest;
import co.syscoop.soberano.test.helper.NewOrderForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(23)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO23_OrderTest_record extends NewOrderActionTest {
	
	protected NewOrderForm newOrderForm = null;

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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label1");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c2mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1002));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {
		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label1");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c2mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1002));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
			
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
		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label1");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c2mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1002));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
			
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label1");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c2mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1002));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(true);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(false);	
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label2");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(true);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c1mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1001));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(8)
	final void testCase8() {
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label3");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c3mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1003));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(true);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(false);
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label4");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(true);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c4mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1004));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c6mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1006));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
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
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_order.zul");
		newOrderForm = new NewOrderForm(desktop,
										desktop.query("textbox").query("#txtLabel").as(Textbox.class),
										desktop.query("combobox").query("#cmbCustomer").as(Combobox.class),
										desktop.query("grid").query("#grdCounters").as(Grid.class),
										desktop.query("south").query("button").query("#btnRecord").as(Button.class),
										desktop.query("grid").getNextSibling().query("include").query("#grd").as(Grid.class));
		try {
			newOrderForm.setComponentValue(newOrderForm.getTxtLabel(), "label5");
			
			desktop.query("checkbox").query("#chkmc2").as(CheckAgent.class).check(false);
			desktop.query("checkbox").query("#chkmc4").as(CheckAgent.class).check(true);	
			
			ComponentAgent cmbCustomerAgent = desktop.query("combobox").query("#cmbCustomer");
			InputAgent cmbCustomerInputAgent = cmbCustomerAgent.as(InputAgent.class);
			cmbCustomerInputAgent.typing("c8mod");
			newOrderForm.setComponentValue(newOrderForm.getCmbCustomer(), new Integer(1008));
			cmbCustomerAgent.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}