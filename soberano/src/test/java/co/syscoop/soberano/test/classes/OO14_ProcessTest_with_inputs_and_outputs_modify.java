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
import org.opentest4j.AssertionFailedError;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.test.helper.ProcessActionTest;
import co.syscoop.soberano.test.helper.ProcessForm;

@Order(14)

//@Disabled

class OO14_ProcessTest_with_inputs_and_outputs_modify extends ProcessActionTest{

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
	
	private void addInput(DesktopAgent desktop,
						String itemQualifiedName,
						BigDecimal quantity,
						Integer unitId,
						String unitQualifiedName) {
		
		ComponentAgent cmbInputAgent = desktop.query("#incDetails").query("combobox").query("#cmbInput");
		InputAgent cmbInputInputAgent = cmbInputAgent.as(InputAgent.class);
		cmbInputInputAgent.typing(itemQualifiedName);			
		Combobox cmbInput = cmbInputAgent.as(Combobox.class);			
		selectComboitemByLabel(cmbInput, itemQualifiedName);			
		cmbInputAgent.click(); 	//needed to force cmbUnit population. 
									//combobox onChange event isn't triggered under testing
		
		ComponentAgent decInputQuantityAgent = desktop.query("#incDetails").query("#incProcessIOs").query("#decInputQuantity");
		Decimalbox decInputQuantity = decInputQuantityAgent.as(Decimalbox.class);			
		setComponentValue(decInputQuantity, quantity);
		
		ComponentAgent cmbInputUnitAgent = desktop.query("#incDetails").query("combobox").query("#cmbInputUnit");
		Combobox cmbInputUnit = cmbInputUnitAgent.as(Combobox.class);			
		selectComboitemByValueForcingLabel(cmbInputUnit, unitId.toString(), unitQualifiedName);
		
		ComponentAgent btnAddInput = cmbInputUnitAgent.query("#btnAddInput");
		btnAddInput.click();
	}
	
	private void addOutput(DesktopAgent desktop,
						String itemQualifiedName,
						BigDecimal quantity,
						Integer unitId,
						String unitQualifiedName,
						Integer weight) {

		ComponentAgent cmbOutputAgent = desktop.query("#incDetails").query("combobox").query("#cmbOutput");
		InputAgent cmbOutputInputAgent = cmbOutputAgent.as(InputAgent.class);
		cmbOutputInputAgent.typing(itemQualifiedName);			
		Combobox cmbOutput = cmbOutputAgent.as(Combobox.class);			
		selectComboitemByLabel(cmbOutput, itemQualifiedName);			
		cmbOutputAgent.click(); 	//needed to force cmbUnit population. 
								//combobox onChange event isn't triggered under testing
		
		ComponentAgent decOutputQuantityAgent = desktop.query("#incDetails").query("#incProcessIOs").query("#decOutputQuantity");
		Decimalbox decOutputQuantity = decOutputQuantityAgent.as(Decimalbox.class);			
		setComponentValue(decOutputQuantity, quantity);
		
		ComponentAgent cmbOutputUnitAgent = desktop.query("#incDetails").query("combobox").query("#cmbOutputUnit");
		Combobox cmbOutputUnit = cmbOutputUnitAgent.as(Combobox.class);			
		selectComboitemByValueForcingLabel(cmbOutputUnit, unitId.toString(), unitQualifiedName);
		
		ComponentAgent intWeightAgent = desktop.query("#incDetails").query("intbox").query("#intWeight");
		Intbox intWeight = intWeightAgent.as(Intbox.class);			
		setComponentValue(intWeight, weight);
		
		ComponentAgent btnAddOutput = cmbOutputUnitAgent.query("#btnAddOutput");
		btnAddOutput.click();
	}

	@Test
	@Order(0)
	final void testCase0() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr2");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(1),
					2,
					"kg : kilogram");
			
			addInput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(1),
					3,
					"g : gram");
			
			addInput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(1),
					7,
					"l : liter");
			
			addOutput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(1000),
					3,
					"g : gram",
					40);
			
			addOutput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(1),
					3,
					"g : gram",
					40);
			
			clickOnApplyButton(processForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testWeightsMustSum100(ex);
		}
	}
	
	@Test
	@Order(1)
	final void testCase1() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr2");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(1),
					2,
					"kg : kilogram");
			
			addInput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(1),
					3,
					"g : gram");
			
			addInput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(1),
					7,
					"l : liter");
			
			addOutput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(1000),
					3,
					"g : gram",
					40);
			
			addOutput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(1),
					3,
					"g : gram",
					60);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr4");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(2),
					7,
					"l : liter");
			
			addInput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(2),
					1,
					"pcs : piece");
			
			addOutput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(500),
					4,
					"mg : milligram",
					30);
			
			addOutput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(1),
					5,
					"lb : pound",
					30);
			
			addOutput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(0.5),
					5,
					"lb : pound",
					40);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr5");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(1),
					2,
					"kg : kilogram");
			
			addOutput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(1),
					5,
					"lb : pound",
					15);
			
			addOutput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(1),
					2,
					"kg : kilogram",
					85);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr6");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(1),
					2,
					"kg : kilogram");
			
			addInput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(2),
					5,
					"lb : pound");
			
			addOutput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(2),
					5,
					"lb : pound",
					90);
			
			addOutput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(1000),
					8,
					"ml : milliliter",
					10);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr7");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(1),
					5,
					"lb : pound");
			
			addInput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(1),
					7,
					"l : liter");
			
			addInput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(200),
					8,
					"ml : milliliter");
			
			addOutput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(3000),
					8,
					"ml : milliliter",
					10);
			
			addOutput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(2000),
					8,
					"ml : milliliter",
					30);
			
			addOutput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(5),
					1,
					"pcs : piece",
					60);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr8");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(1),
					1,
					"pcs : piece");
			
			addOutput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(300),
					8,
					"ml : milliliter",
					50);
			
			addOutput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(2),
					1,
					"pcs : piece",
					50);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr9");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(3000),
					4,
					"mg : milligram");
			
			addInput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(1000),
					3,
					"g : gram");
			
			addOutput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(1),
					1,
					"pcs : piece",
					100);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(8)
	final void testCase8() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product1");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(2),
					2,
					"kg : kilogram");
			
			addInput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(2),
					5,
					"lb : pound");
			
			addInput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(2),
					5,
					"lb : pound");
			
			addOutput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(1),
					2,
					"kg : kilogram",
					0);
			
			addOutput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(1000),
					4,
					"mg : milligram",
					0);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product2");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(100),
					3,
					"g : gram");
			
			addInput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(300),
					8,
					"ml : milliliter");
			
			addInput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(1),
					7,
					"l : liter");
			
			addOutput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(1),
					2,
					"kg : kilogram",
					0);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(10)
	final void testCase10() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product3");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(1),
					1,
					"pcs : piece");
			
			addInput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(5000),
					4,
					"mg : milligram");
			
			addInput(desktop,
					"mmaterial4 : mm4",
					new BigDecimal(1),
					5,
					"lb : pound");
			
			addOutput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(0.5),
					2,
					"kg : kilogram",
					0);
			
			addOutput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(2.5),
					7,
					"l : liter",
					0);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(11)
	final void testCase11() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product4");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial5 : mm5",
					new BigDecimal(2000),
					4,
					"mg : milligram");
			
			addInput(desktop,
					"mmaterial6 : mm6",
					new BigDecimal(2),
					2,
					"kg : kilogram");
			
			addInput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(1.3),
					7,
					"l : liter");
			
			addOutput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(350),
					8,
					"ml : milliliter",
					0);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product5");
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addInput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(0.7),
					7,
					"l : liter");
			
			addInput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(2),
					1,
					"pcs : piece");
			
			addOutput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(1),
					1,
					"pcs : piece",
					0);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(13)
	final void testCase13() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr2");
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnInputRowDeletion" + "mm6");
			btnInputRowDeletion.click();
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm4");
			btnOutputRowDeletion.click();
			
			clickOnApplyButton(processForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testWeightsMustSum100(ex);
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr2");
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnInputRowDeletion" + "mm6");
			btnInputRowDeletion.click();
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm4");
			btnOutputRowDeletion.click();
			
			//update weights to sum 100%
			btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm2");
			btnOutputRowDeletion.click();
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addOutput(desktop,
					"mmaterial2 : mm2",
					new BigDecimal(1000),
					3,
					"g : gram",
					100);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(15)
	final void testCase15() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr6");
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm6");
			btnOutputRowDeletion.click();
			
			//update weights to sum 100%
			btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm7");
			btnOutputRowDeletion.click();
			
			DesktopAgent desktop = processForm.getDesktop();
			
			addOutput(desktop,
					"mmaterial7 : mm7",
					new BigDecimal(1000),
					8,
					"ml : milliliter",
					100);
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(16)
	final void testCase16() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr7");
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnInputRowDeletion" + "mm7");
			btnInputRowDeletion.click();
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(17)
	final void testCase17() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr9");
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnInputRowDeletion" + "mm2");
			btnInputRowDeletion.click();
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(18)
	final void testCase18() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product1");
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnInputRowDeletion" + "mm2");
			btnInputRowDeletion.click();
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(19)
	final void testCase19() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product2");
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm5");
			btnOutputRowDeletion.click();
						
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(20)
	final void testCase20() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("product5");
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#incProcessIOs").query("#btnOutputRowDeletion" + "mm9");
			btnOutputRowDeletion.click();
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}

	private void checkProcessInputs(String processName,
									String itemNames[],
									Double quantities[],
									String units[]) {

		ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
		loadObjectDetails(processName);
		ComponentAgent tchdnInputsAgent = processForm.getDesktop().query("#incDetails").query("#incProcessIOs").query("treechildren").query("#tchdnInputs");
		Treechildren tchdnInputs = tchdnInputsAgent.as(Treechildren.class);
		for (int i = 0; i < itemNames.length; i++) {
			String shownInputName = ((Treeitem) tchdnInputs.getChildren().get(i)).getLabel();
			assertEquals(itemNames[i], shownInputName, "Wrong input shown for process " +  processName + ". Expected input name: " + itemNames[i] + ". Shown input name: " + shownInputName);
			
			String shownInputItemCode = ((Treeitem) tchdnInputs.getChildren().get(i)).getValue();
			Decimalbox decInputQuantity = (Decimalbox) tchdnInputs.query("#decInputQuantity" + shownInputItemCode);
			assertEquals(decInputQuantity.getValue().subtract(new BigDecimal(quantities[i])).abs().doubleValue() < 0.00000001, true, "Wrong quantity shown for input " + itemNames[i] + " of process " +  processName + ". Expected quantity: " + quantities[i].toString() + ". Shown quantity " + decInputQuantity.getValue().toPlainString());
			
			Label lblInputUnit = (Label) tchdnInputs.query("#lblInputUnit" + shownInputItemCode);
			assertEquals(lblInputUnit.getValue(), units[i], "Wrong unit shown for input " + itemNames[i] + " of process " +  processName + ". Expected unit: " + units[i] + ". Shown unit " + lblInputUnit.getValue());
		}
	}

	private void checkProcessOutputs(String processName,
									String itemNames[],
									Double quantities[],
									String units[],
									Integer weights[]) {

		ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
		loadObjectDetails(processName);
		ComponentAgent tchdnOutputsAgent = processForm.getDesktop().query("#incDetails").query("#incProcessIOs").query("treechildren").query("#tchdnOutputs");
		Treechildren tchdnOutputs = tchdnOutputsAgent.as(Treechildren.class);
		for (int i = 0; i < itemNames.length; i++) {
			String shownOutputName = ((Treeitem) tchdnOutputs.getChildren().get(i)).getLabel();
			assertEquals(itemNames[i], shownOutputName, "Wrong output shown for process " +  processName + ". Expected output name: " + itemNames[i] + ". Shown output name: " + shownOutputName);
			
			String shownOutputItemCode = ((Treeitem) tchdnOutputs.getChildren().get(i)).getValue();
			Decimalbox decOutputQuantity = (Decimalbox) tchdnOutputs.query("#decOutputQuantity" + shownOutputItemCode);
			assertEquals(decOutputQuantity.getValue().subtract(new BigDecimal(quantities[i])).abs().doubleValue() < 0.00000001, true, "Wrong quantity shown for output " + itemNames[i] + " of process " +  processName + ". Expected quantity: " + quantities[i].toString() + ". Shown quantity " + decOutputQuantity.getValue().toPlainString());
			
			Label lblOutputUnit = (Label) tchdnOutputs.query("#lblOutputUnit" + shownOutputItemCode);
			assertEquals(units[i], lblOutputUnit.getValue(), "Wrong unit shown for output " + itemNames[i] + " of process " +  processName + ". Expected unit: " + units[i] + ". Shown unit " + lblOutputUnit.getValue());
			
			Intbox intWeight = (Intbox) tchdnOutputs.query("#intWeight" + shownOutputItemCode);
			assertEquals(weights[i], intWeight.getValue(), "Wrong weight shown for output " + itemNames[i] + " of process " +  processName + ". Expected weight: " + weights[i] + ". Shown weight " + intWeight.getValue());
		}
	}

	@Test
	@Order(21)
	final void testCase21() {
	
		try {
			checkProcessInputs("mpr2",
								new String[] {"mmaterial5", "mmaterial7"},
								new Double[] {2.2046226218, 1000.0},
								new String[] {"lb", "ml"});
			
			checkProcessOutputs("mpr2",
								new String[] {"mmaterial2"},
								new Double[] {1.0},
								new String[] {"kg"},
								new Integer[] {100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(22)
	final void testCase22() {
	
		try {
			checkProcessInputs("mpr4",
								new String[] {"mmaterial8", "mmaterial9"},
								new Double[] {2000.0, 2.0},
								new String[] {"ml", "pcs"});
			
			checkProcessOutputs("mpr4",
								new String[] {"mmaterial4", "mmaterial5", "mmaterial6"},
								new Double[] {500.0, 1.0, 0.5 * 453592},
								new String[] {"mg", "lb", "mg"},
								new Integer[] {30, 30, 40});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(23)
	final void testCase23() {
	
		try {
			checkProcessInputs("mpr5",
								new String[] {"mmaterial2"},
								new Double[] {1.0},
								new String[] {"kg"});
			
			checkProcessOutputs("mpr5",
								new String[] {"mmaterial5", "mmaterial6"},
								new Double[] {1.0, 1000000.0},
								new String[] {"lb", "mg"},
								new Integer[] {15, 85});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(24)
	final void testCase24() {
	
		try {
			checkProcessInputs("mpr6",
								new String[] {"mmaterial4", "mmaterial5"},
								new Double[] {1000000.0, 2.0},
								new String[] {"mg", "lb"});
			
			checkProcessOutputs("mpr6",
								new String[] {"mmaterial7"},
								new Double[] {1000.0},
								new String[] {"ml"},
								new Integer[] {100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(25)
	final void testCase25() {
	
		try {
			checkProcessInputs("mpr7",
								new String[] {"mmaterial6", "mmaterial8"},
								new Double[] {453592.0, 200.0},
								new String[] {"mg", "ml"});
			
			checkProcessOutputs("mpr7",
								new String[] {"mmaterial7", "mmaterial8", "mmaterial9"},
								new Double[] {3000.0, 2000.0, 5.0},
								new String[] {"ml", "ml", "pcs"},
								new Integer[] {10, 30, 60});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(26)
	final void testCase26() {
	
		try {
			checkProcessInputs("mpr8",
								new String[] {"mmaterial9"},
								new Double[] {1.0},
								new String[] {"pcs"});
			
			checkProcessOutputs("mpr8",
								new String[] {"mmaterial8", "mmaterial9"},
								new Double[] {300.0, 2.0},
								new String[] {"ml", "pcs"},
								new Integer[] {50, 50});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(27)
	final void testCase27() {
	
		try {
			checkProcessInputs("mpr9",
								new String[] {"mmaterial4"},
								new Double[] {1000000.0},
								new String[] {"mg"});
			
			checkProcessOutputs("mpr9",
								new String[] {"mmaterial9"},
								new Double[] {1.0},
								new String[] {"pcs"},
								new Integer[] {100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(28)
	final void testCase28() {
	
		try {
			checkProcessInputs("product1",
								new String[] {"mmaterial4", "mmaterial5"},
								new Double[] {2 * 453592.0, 2.0},
								new String[] {"mg", "lb"});
			
			checkProcessOutputs("product1",
								new String[] {"mmaterial2", "mmaterial4", "mproduct1"},
								new Double[] {1.0, 1000.0, 1.0},
								new String[] {"kg", "mg", "kg"},
								new Integer[] {0, 0, 100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(29)
	final void testCase29() {
	
		try {
			checkProcessInputs("product2",
								new String[] {"mmaterial6", "mmaterial7", "mmaterial8"},
								new Double[] {100000.0, 300.0, 1000.0},
								new String[] {"mg", "ml", "ml"});
			
			checkProcessOutputs("product2",
								new String[] {"mproduct2"},
								new Double[] {1.0},
								new String[] {"pcs"},
								new Integer[] {100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(30)
	final void testCase30() {
	
		try {
			checkProcessInputs("product3",
								new String[] {"mmaterial2", "mmaterial4", "mmaterial9"},
								new Double[] {0.005, 453592.0, 1.0},
								new String[] {"kg", "mg", "pcs"});
			
			checkProcessOutputs("product3",
								new String[] {"mmaterial6", "mmaterial7", "mproduct3"},
								new Double[] {0.5 * 1000 * 1000, 2500.0, 1.0},
								new String[] {"mg", "ml", "ml"},
								new Integer[] {0, 0, 100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(31)
	final void testCase31() {
	
		try {
			checkProcessInputs("product4",
								new String[] {"mmaterial5", "mmaterial6", "mmaterial7"},
								new Double[] {2000 * 0.0000022046, 2000000.0, 1300.0},
								new String[] {"lb", "mg", "ml"});
			
			checkProcessOutputs("product4",
								new String[] {"mmaterial8", "mproduct4"},
								new Double[] {350.0, 1.0},
								new String[] {"ml", "lb"},
								new Integer[] {0, 100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(32)
	final void testCase32() {
	
		try {
			checkProcessInputs("product5",
								new String[] {"mmaterial8", "mmaterial9"},
								new Double[] {700.0, 2.0},
								new String[] {"ml", "pcs"});
			
			checkProcessOutputs("product5",
								new String[] {"mproduct5"},
								new Double[] {1.0},
								new String[] {"pcs"},
								new Integer[] {100});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
}
