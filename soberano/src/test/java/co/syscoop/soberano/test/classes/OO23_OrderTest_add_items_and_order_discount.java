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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.OrderActionTest;
import co.syscoop.soberano.test.helper.OrderForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(23)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO23_OrderTest_add_items_and_order_discount extends OrderActionTest {
	
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
									desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
									desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
									desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
									desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
									desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
									desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
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
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
						
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
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
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
						
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(1));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(2));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 1");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(3));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(4));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 3");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(543.321));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(5));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 4");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(10));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 5");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(10));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct1 : mp1");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1001));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 6");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(5));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "2", "kg : kilogram");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 7");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(7));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 8");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(8));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(9));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent cmbItemToOrderAgent = desktop.query("combobox").query("#cmbItemToOrder");
			InputAgent cmbItemToOrderAgentInputAgent = cmbItemToOrderAgent.as(InputAgent.class);
			cmbItemToOrderAgentInputAgent.typing("mproduct7 : mp7");
			orderForm.setComponentValue(orderForm.getCmbItemToOrder(), new Integer(1007));
			cmbItemToOrderAgent.click(); 	//needed to force comboitem selection composer handling under testing
			
			orderForm.setComponentValue(orderForm.getTxtSpecialInstructions(), "Instructions 10");			
			orderForm.setComponentValue(orderForm.getDecQuantity(), new BigDecimal(10));
			orderForm.selectComboitemByValueForcingLabel(orderForm.getCmbUnit(), "1", "pcs : piece");
			
			desktop.query("combobox").query("#cmbUnit").query("#btnMake").click();
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
			
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
						
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");

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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
			
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");

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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
			
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");

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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
				desktop.query("intbox").query("#intDiscountTop").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountTop").as(Decimalbox.class),
				desktop.query("intbox").query("#intDiscountBottom").as(Intbox.class),
				desktop.query("decimalbox").query("#decAmountBottom").as(Decimalbox.class),
				desktop.query("div").query("#divOrderItems").as(Div.class),
				desktop.query("south").query("textbox").query("#txtStage").as(Textbox.class));				
		try {
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
			
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");

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
				desktop.query("textbox").query("#txtCustomer").as(Textbox.class),
				desktop.query("combobox").query("#cmbItemToOrder").as(Combobox.class),
				desktop.query("textbox").query("#txtSpecialInstructions").as(Textbox.class),
				desktop.query("textbox").query("#decQuantity").as(Decimalbox.class),																					
				desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
				desktop.query("combobox").query("#cmbUnit").query("#btnMake").as(Button.class),
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
			
			ComponentAgent divOrderItemsAgent = desktop.query("combobox").query("#divOrderItems");
			
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
}