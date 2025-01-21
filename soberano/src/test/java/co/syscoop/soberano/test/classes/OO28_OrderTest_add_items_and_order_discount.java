package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.OrderActionTest;
import co.syscoop.soberano.test.helper.OrderForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(28)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO28_OrderTest_add_items_and_order_discount extends OrderActionTest {
	
	protected OrderForm orderForm = null;

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
	@Order(0)
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
									desktop.query("textbox").query("#txtLabel").as(Textbox.class),
									desktop.query("textbox").query("#txtCounters").as(Textbox.class),
									desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
									desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
									desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
									desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
									desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
									desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
									desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
									desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
									desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
									desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
									desktop.query("div").query("#divOrderItems").as(Div.class),
									desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));		
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			orderForm.testEachConstrainedObjectIsDeclared();
			orderForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(1));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 0);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
						
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

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(1));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 0);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
						
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(1));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(2));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1002");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 1");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(3));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1003");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(4));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1004");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 3");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(543.321));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1004");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(5));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1005");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 4");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(10));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1005");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 5");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(6));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1006");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 6");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(5));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1006");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 7");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(7));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1007");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 8");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(8));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1008");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(9));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1009");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("textbox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 10");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(10));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("textbox").query("#cmbUnit").query("#btnMake").click();
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1009");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(100));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing
			
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1010");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(1));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
			
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
	@Order(23)
	final void testCase23() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1002");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 1);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1011");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(2));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing
			
			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1011");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(2));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1004");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {			
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 5);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
						
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1013");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(543.321 - 300.123));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1013");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(400));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1004");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1014");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(1));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1014");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(1));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1005");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {			
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 0);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1015");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(0));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1015");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(0));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1005");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1016");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(0));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1016");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(3));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(28)
	final void testCase28() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1006");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {		
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 0);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1017");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(5));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1017");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(1));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(29)
	final void testCase29() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1006");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1018");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(3));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1018");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(7));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(30)
	final void testCase30() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1008");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {			
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 0);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1020");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(2));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1020");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(3));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(31)
	final void testCase31() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1009");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {			
			orderForm.setComponentValue(orderForm.getIntDiscountTop(), 100);
			ComponentAgent intDiscountTopAgent = desktop.query("intbox").query("#intDiscountTop");
			intDiscountTopAgent.click();
			
			ComponentAgent divOrderItemsAgent = desktop.query("textbox").query("#wndOrderItems").query("#divOrderItems");
			
			ComponentAgent decDiscountAgent = divOrderItemsAgent.query("#decDiscount1021");
			Decimalbox decDiscount = decDiscountAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decDiscount, new BigDecimal(5));
			decDiscountAgent.click(); //needed to force decimalbox value change handling under testing

			ComponentAgent decServedItemsAgent = divOrderItemsAgent.query("#decServedItems1021");
			Decimalbox decServedItems = decServedItemsAgent.as(Decimalbox.class);
			orderForm.setComponentValue(decServedItems, new BigDecimal(10));
			decServedItemsAgent.click(); //needed to force decimalbox value change handling under testing
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(32)
	final void testCase32() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1001");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1009);
			processRuns.add(1010);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1009, "mcat3");
			categories.put(1010, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1009, "");
			instructions.put(1010, "");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1009, "mproduct1");
			itemNames.put(1010, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1009, "kg");
			units.put(1010, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1009, new BigDecimal(1).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunServed.put(1010, new BigDecimal(2).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1009, "1.00000000");
			processRunOrdered.put(1010, "2.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1009, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunDiscounted.put(1010, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1001,
					"label1",
					0,
					new BigDecimal(13.87805360),
					"mc4,",
					"Customer2fnmod Customer2lnmod : c2mod@soberano.syscoop.co",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(33)
	final void testCase33() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1002");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1011);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1011, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1011, "Instructions 1");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1011, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1011, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1011, new BigDecimal(2).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1011, "3.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1011, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1002,
					"",
					1,
					new BigDecimal(10.32067802),
					"mc2,",
					"",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(34)
	final void testCase34() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1003");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1012);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1012, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1012, "");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1012, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1012, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1012, new BigDecimal(4).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1012, "4.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1012, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1003,
					"label2",
					0,
					new BigDecimal(22.93484004),
					"mc4,",
					"",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(35)
	final void testCase35() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1004");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1013);
			processRuns.add(1014);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1013, "mcat3");
			categories.put(1014, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1013, "Instructions 3");
			instructions.put(1014, "");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1013, "mproduct1");
			itemNames.put(1014, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1013, "kg");
			units.put(1014, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1013, new BigDecimal(543.321 - 300.123).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunServed.put(1014, new BigDecimal(5 - 4).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1013, "543.32100000");
			processRunOrdered.put(1014, "5.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1013, new BigDecimal(543.321 - 300.123).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunDiscounted.put(1014, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1004,
					"",
					5,
					new BigDecimal(4.95184046),
					"mc2,",
					"Customer1fnmod Customer1lnmod : c1mod@soberano.syscoop.co",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(36)
	final void testCase36() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1005");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1015);
			processRuns.add(1016);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1015, "mcat3");
			categories.put(1016, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1015, "Instructions 4");
			instructions.put(1016, "Instructions 5");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1015, "mproduct1");
			itemNames.put(1016, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1015, "kg");
			units.put(1016, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1015, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunServed.put(1016, new BigDecimal(3).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1015, "10.00000000");
			processRunOrdered.put(1016, "6.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1015, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunDiscounted.put(1016, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1005,
					"label3",
					0,
					new BigDecimal(17.20113003),
					"mc4,",
					"Customer3fnmod Customer3lnmod : c3mod@soberano.syscoop.co",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(37)
	final void testCase37() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1006");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1017);
			processRuns.add(1018);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1017, "mcat3");
			categories.put(1018, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1017, "Instructions 6");
			instructions.put(1018, "Instructions 7");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1017, "mproduct1");
			itemNames.put(1018, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1017, "kg");
			units.put(1018, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1017, new BigDecimal(5).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunServed.put(1018, new BigDecimal(7).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1017, "5.00000000");
			processRunOrdered.put(1018, "7.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1017, new BigDecimal(1).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			processRunDiscounted.put(1018, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1006,
					"",
					0,
					new BigDecimal(54.15888863),
					"mc2,",
					"",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(38)
	final void testCase38() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1007");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1019);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1019, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1019, "Instructions 8");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1019, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1019, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1019, new BigDecimal(8).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1019, "8.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1019, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1007,
					"label4",
					0,
					new BigDecimal(39.36452546),
					"mc2,",
					"Customer4fnmod Customer4lnmod : c4mod@soberano.syscoop.co",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(39)
	final void testCase39() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1008");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1020);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1020, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1020, "");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1020, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1020, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1020, new BigDecimal(2).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1020, "9.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1020, new BigDecimal(2).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1008,
					"",
					0,
					new BigDecimal(0.00000000),
					"mc4,",
					"Customer6fnmod Customer6lnmod : c6mod@soberano.syscoop.co",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(40)
	final void testCase40() {

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/order.zul?id=1009");
		orderForm = new OrderForm(desktop, 
				desktop.query("textbox").query("#txtLabel").as(Textbox.class),
				desktop.query("textbox").query("#txtCounters").as(Textbox.class),
				desktop.query("textbox").query("#cmbCustomer").as(Combobox.class),
				desktop.query("textbox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("textbox").query("#cmbUnit").as(Combobox.class),
				desktop.query("textbox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ArrayList<Integer> processRuns = new ArrayList<Integer>();
			processRuns.add(1021);
			
			HashMap<Integer, String> categories = new HashMap<Integer, String>();
			categories.put(1021, "mcat8");
			
			HashMap<Integer, String> instructions = new HashMap<Integer, String>();
			instructions.put(1021, "Instructions 10");
			
			HashMap<Integer, String> itemNames = new HashMap<Integer, String>();
			itemNames.put(1021, "mproduct7");
			
			HashMap<Integer, String> units = new HashMap<Integer, String>();
			units.put(1021, "pcs");		
			
			HashMap<Integer, BigDecimal> processRunServed = new HashMap<Integer, BigDecimal>();
			processRunServed.put(1021, new BigDecimal(10).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
			
			HashMap<Integer, String> processRunOrdered = new HashMap<Integer, String>();
			processRunOrdered.put(1021, "10.00000000");
			
			HashMap<Integer, BigDecimal> processRunDiscounted = new HashMap<Integer, BigDecimal>();
			processRunDiscounted.put(1021, new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros());
	
			testOrder(orderForm,
					1009,
					"label5",
					100,
					new BigDecimal(0.00000000),
					"mc4,",
					"Customer8fnmod Customer8lnmod : c8mod@soberano.syscoop.co",
					processRuns,
					categories,
					instructions,					
					itemNames,
					units,
					processRunServed,
					processRunOrdered,
					processRunDiscounted);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}