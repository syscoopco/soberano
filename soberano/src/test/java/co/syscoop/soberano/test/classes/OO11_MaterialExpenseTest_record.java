package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.MaterialExpenseActionTest;
import co.syscoop.soberano.test.helper.MaterialExpenseForm;
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
}