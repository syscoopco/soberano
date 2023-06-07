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

import co.syscoop.soberano.test.helper.MaterialExpenseActionTest;
import co.syscoop.soberano.test.helper.MaterialExpenseForm;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(11)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO11_MaterialExpenseTest_record extends MaterialExpenseActionTest {
	
	protected MaterialExpenseForm materialExpenseForm = null;

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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
						
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
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
			cal.add(Calendar.DAY_OF_YEAR, -6);
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
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
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "2", "kg : kilogram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(-2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(-2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.00000100));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(17)
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(19)
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(-2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.00000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(21)
	final void testCase21() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(22)
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(24)
	final void testCase24() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(25)
	final void testCase25() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(26)
	final void testCase26() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
			clickOnInputFormRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//It fails due to wrong values since user 18 is a salesclerk. Hence, user 18 can not retrieve
			//providers, materials, so can't populate the corresponding combos.
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(27)
	final void testCase27() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(28)
	final void testCase28() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(29)
	final void testCase29() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(30)
	final void testCase30() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(-2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(31)
	final void testCase31() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(-2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(32)
	final void testCase32() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(33)
	final void testCase33() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(34)
	final void testCase34() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(35)
	final void testCase35() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(36)
	final void testCase36() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(37)
	final void testCase37() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(38)
	final void testCase38() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(39)
	final void testCase39() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(40)
	final void testCase40() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(0.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(41)
	final void testCase41() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(42)
	final void testCase42() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(-2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(43)
	final void testCase43() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1001));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(44)
	final void testCase44() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm4");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1004));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "1", "pcs : piece");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(45)
	final void testCase45() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(46)
	final void testCase46() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "8", "ml : milliliter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(-2000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1002));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "");
			
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
	@Order(47)
	final void testCase47() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("combobox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("combobox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("combobox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), new Date());
			
			ComponentAgent cmbProviderAgent = desktop.query("combobox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), new Integer(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), new Integer(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "4", "mg : milligram");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("combobox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), new Integer(1003));
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getTxtReference(), "abc123");
			
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
	@Order(48)
	final void testCase48() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(8, grd.getRows().getChildren().size(), "Wrong count of recorded material expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
										"mprov1",
										"mmaterial6",
										"2000.00000000 mg",
										0.000001,
										"mc3",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
										"mprov2",
										"mmaterial2",
										"0.00000100 mg",
										1.000001,
										"mc1",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
										"mprov1",
										"mmaterial2",
										"2000.00000000 lb",
										3000000.0,
										"mc3",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(3), 
										"mprov1",
										"mmaterial7",
										"1.00000100 l",
										0.000001,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(4), 
										"mprov1",
										"mmaterial2",
										"1.00000000 mg",
										3000000.0,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(5), 
										"mprov2",
										"mmaterial6",
										"3000000.00000000 lb",
										1.0,
										"mc1",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(6), 
										"mprov1",
										"mmaterial4",
										"3000000.00000000 lb",
										1.000001,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(7), 
										"mprov2",
										"mmaterial6",
										"1.00000000 mg",
										0.000001,
										"mc3",
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