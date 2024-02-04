package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.PayrollExpenseActionTest;
import co.syscoop.soberano.test.helper.PayrollExpenseForm;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(8)

//TODO: enable test
@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O8_PayrollExpenseTest_record extends PayrollExpenseActionTest {
	
	protected PayrollExpenseForm payrollExpenseForm = null;

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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(5)
	@Disabled
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			//From LogicalQueriesForSoberanoInstance.java, the shift corresponding to the day before is closed
			/*
			INSERT INTO soberano.\"ShiftClosure\"(\"This_is_identified_by_EntityTypeInstance_id\", \n"
			+ "									\"This_is_of_Shift\", \n"
			+ "									\"Report_is_of_This\")\n"
			+ "	VALUES (13, \n"
			+ "			now() - INTERVAL '1 day', \n"
			+ "			'');",
			*/
			Date closedShift = new Date(); //in closedShift, current day
			Calendar cal = Calendar.getInstance();
			cal.setTime(closedShift);
			cal.add(Calendar.DAY_OF_YEAR, -10);
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testShiftHasBeenClosedException(ex);
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			Date closedShift = new Date(); //in closedShift, current day
			Calendar cal = Calendar.getInstance();
			cal.setTime(closedShift);
			cal.add(Calendar.DAY_OF_YEAR, 1); //try to record an expense for a future date
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testWrongDateTimeException(ex);
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
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
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user@1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(19)
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(20)
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "abc123");
			
			clickOnInputFormRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(24)
	final void testCase24() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user1@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1003));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
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
	@Order(25)
	final void testCase25() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		PayrollExpenseForm payrollExpenseForm = new PayrollExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbWorker")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbWorker(), new Integer(1002));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getCmbCurrency(), new Integer(1001));
			
			payrollExpenseForm.setComponentValue(payrollExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/payroll_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(5, grd.getRows().getChildren().size(), "Wrong count of recorded payroll expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
										"user2fn user2ln : user2@soberano.syscoop.co",
										"",
										"",
										0.000001,
										"mc1",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
										"user3fn user3ln : user3@soberano.syscoop.co",
										"",
										"",
										3000000.0,
										"mc3",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
										"user1fn user1ln : user1@soberano.syscoop.co",
										"",
										"",
										1.000001,
										"mc1",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(3), 
										"user3fn user3ln : user3@soberano.syscoop.co",
										"",
										"",
										1.0,
										"mc1",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(4), 
										"user2fn user2ln : user2@soberano.syscoop.co",
										"",
										"",
										2000.0,
										"mc2",
										"abc123");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}