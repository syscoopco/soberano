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

@Order(19)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO19_MaterialExpenseTest_record_cancelled_expense_again extends MaterialExpenseActionTest {
	
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
	@Order(22)
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("textbox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("textbox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("textbox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			Date closedShift = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(closedShift);
			cal.add(Calendar.DAY_OF_YEAR, -6);
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbProviderAgent = desktop.query("textbox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), Integer.valueOf(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("textbox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm6");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), Integer.valueOf(1006));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(3000000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(1));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("textbox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), Integer.valueOf(1001));
			
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
	@Order(27)
	final void testCase27() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("textbox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("textbox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("textbox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			Date closedShift = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(closedShift);
			cal.add(Calendar.DAY_OF_YEAR, -4);
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbProviderAgent = desktop.query("textbox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), Integer.valueOf(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("textbox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm7");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), Integer.valueOf(1007));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(1.000001));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "7", "l : liter");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(0.000001));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("textbox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), Integer.valueOf(1002));
			
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
	@Order(29)
	final void testCase29() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		MaterialExpenseForm materialExpenseForm = new MaterialExpenseForm(desktop, 
													(desktop.query("datebox").query("#dateExpenseDate")).as(Datebox.class), 
													(desktop.query("textbox").query("#cmbProvider")).as(Combobox.class),
													(desktop.query("textbox").query("#cmbMaterial")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decQuantity")).as(Decimalbox.class),
													(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
													(desktop.query("decimalbox").query("#decAmount")).as(Decimalbox.class),
													(desktop.query("textbox").query("#cmbCurrency")).as(Combobox.class),
													(desktop.query("textbox").query("#txtReference")).as(Textbox.class),
													(desktop.query("datebox").query("#boxDetails").query("#btnRecord")).as(Button.class),
													(desktop.query("grid").query("#grd")).as(Grid.class));		
		try {
			Date closedShift = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(closedShift);
			cal.add(Calendar.DAY_OF_YEAR, -3);
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDateExpenseDate(), cal.getTime());
			
			ComponentAgent cmbProviderAgent = desktop.query("textbox").query("#cmbProvider");
			InputAgent cmbProviderInputAgent = cmbProviderAgent.as(InputAgent.class);
			cmbProviderInputAgent.typing("mprov1");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbProvider(), Integer.valueOf(1001));
			
			ComponentAgent cmbMaterialAgent = desktop.query("textbox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mm2");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbMaterial(), Integer.valueOf(1002));
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecQuantity(), new BigDecimal(2000));
			materialExpenseForm.selectComboitemByValueForcingLabel(materialExpenseForm.getCmbUnit(), "5", "lb : pound");
			materialExpenseForm.setComponentValue(materialExpenseForm.getDecAmount(), new BigDecimal(3000000));
			
			ComponentAgent cmbCurrencyAgent = desktop.query("textbox").query("#cmbCurrency");
			InputAgent cmbCurrencyInputAgent = cmbCurrencyAgent.as(InputAgent.class);
			cmbCurrencyInputAgent.typing("mc3");
			materialExpenseForm.setComponentValue(materialExpenseForm.getCmbCurrency(), Integer.valueOf(1003));
			
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
	@Order(48)
	final void testCase48() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(13, grd.getRows().getChildren().size(), "Wrong count of recorded material expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
										"mprov2",
										"mmaterial6",
										"3000000.00000000 lb",
										1.0,
										"mc1",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
										"mprov1",
										"mmaterial7",
										"1.00000100 l",
										0.000001,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
										"mprov1",
										"mmaterial2",
										"2000.00000000 lb",
										3000000.0,
										"mc3",
										"");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}