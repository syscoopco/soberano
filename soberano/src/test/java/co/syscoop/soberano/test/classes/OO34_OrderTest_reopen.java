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
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Intbox;
import co.syscoop.soberano.test.helper.PrintTicketActionTest;
import co.syscoop.soberano.test.helper.PrintTicketForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(34)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO34_OrderTest_reopen extends PrintTicketActionTest {
	
	protected PrintTicketForm printTicketForm = null;

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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co"); //user 1 isn't a reopener
		DesktopAgent desktop = Zats.newClient().connect("/print_ticket.zul");
		PrintTicketForm printTicketForm = new PrintTicketForm(desktop,
																desktop.query("#wndContentPanel").query("#intOrderNumber").as(Intbox.class),
																desktop.query("#wndContentPanel").query("#btnRetrieve").as(Button.class),
																desktop.query("#wndContentPanel").query("#txtReport").as(Textbox.class),
																desktop.query("#incSouth").query("#btnReopen").as(Button.class));	
		try {
			printTicketForm.setComponentValue(printTicketForm.getIntOrderNumber(), 1000000); //inexistent order
			
			ComponentAgent btnRetrieve = desktop.query("#wndContentPanel").query("#btnRetrieve");
			btnRetrieve.click();			
						
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co"); //user 1 isn't a reopener
		DesktopAgent desktop = Zats.newClient().connect("/print_ticket.zul");
		PrintTicketForm printTicketForm = new PrintTicketForm(desktop,
																desktop.query("#wndContentPanel").query("#intOrderNumber").as(Intbox.class),
																desktop.query("#wndContentPanel").query("#btnRetrieve").as(Button.class),
																desktop.query("#wndContentPanel").query("#txtReport").as(Textbox.class),
																desktop.query("#incSouth").query("#btnReopen").as(Button.class));	
		try {
			printTicketForm.setComponentValue(printTicketForm.getIntOrderNumber(), 1001);
			
			ComponentAgent btnRetrieve = desktop.query("#wndContentPanel").query("#btnRetrieve");
			btnRetrieve.click();			
			
			ComponentAgent btnReopen = desktop.query("#incSouth").query("#btnReopen");
			btnReopen.click();
			btnReopen.click();	
			
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co"); //user 22 is a reopener
		DesktopAgent desktop = Zats.newClient().connect("/print_ticket.zul");
		PrintTicketForm printTicketForm = new PrintTicketForm(desktop,
																desktop.query("#wndContentPanel").query("#intOrderNumber").as(Intbox.class),
																desktop.query("#wndContentPanel").query("#btnRetrieve").as(Button.class),
																desktop.query("#wndContentPanel").query("#txtReport").as(Textbox.class),
																desktop.query("#incSouth").query("#btnReopen").as(Button.class));	
		try {
			printTicketForm.setComponentValue(printTicketForm.getIntOrderNumber(), 1001);
			
			ComponentAgent btnRetrieve = desktop.query("#wndContentPanel").query("#btnRetrieve");
			btnRetrieve.click();			
			
			ComponentAgent btnReopen = desktop.query("#incSouth").query("#btnReopen");
			btnReopen.click();
			btnReopen.click();	
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co"); //user 22 is a reopener
		DesktopAgent desktop = Zats.newClient().connect("/print_ticket.zul");
		PrintTicketForm printTicketForm = new PrintTicketForm(desktop,
																desktop.query("#wndContentPanel").query("#intOrderNumber").as(Intbox.class),
																desktop.query("#wndContentPanel").query("#btnRetrieve").as(Button.class),
																desktop.query("#wndContentPanel").query("#txtReport").as(Textbox.class),
																desktop.query("#incSouth").query("#btnReopen").as(Button.class));	
		try {
			printTicketForm.setComponentValue(printTicketForm.getIntOrderNumber(), 1002);
			
			ComponentAgent btnRetrieve = desktop.query("#wndContentPanel").query("#btnRetrieve");
			btnRetrieve.click();			
			
			ComponentAgent btnReopen = desktop.query("#incSouth").query("#btnReopen");
			btnReopen.click();
			btnReopen.click();	
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

		SpringUtility.setLoggedUserForTesting("user22@soberano.syscoop.co"); //user 22 is a reopener
		DesktopAgent desktop = Zats.newClient().connect("/print_ticket.zul");
		PrintTicketForm printTicketForm = new PrintTicketForm(desktop,
																desktop.query("#wndContentPanel").query("#intOrderNumber").as(Intbox.class),
																desktop.query("#wndContentPanel").query("#btnRetrieve").as(Button.class),
																desktop.query("#wndContentPanel").query("#txtReport").as(Textbox.class),
																desktop.query("#incSouth").query("#btnReopen").as(Button.class));	
		try {
			printTicketForm.setComponentValue(printTicketForm.getIntOrderNumber(), 1006);
			
			ComponentAgent btnRetrieve = desktop.query("#wndContentPanel").query("#btnRetrieve");
			btnRetrieve.click();			
			
			ComponentAgent btnReopen = desktop.query("#incSouth").query("#btnReopen");
			btnReopen.click();
			btnReopen.click();	
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}