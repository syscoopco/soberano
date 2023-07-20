package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import co.syscoop.soberano.test.helper.ProcessActionTest;
import co.syscoop.soberano.test.helper.ProcessForm;

@Order(14)

//TODO: enable test
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
		
		ComponentAgent decInputQuantityAgent = desktop.query("#incDetails").query("decimalbox").query("#decInputQuantity");
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
		
		ComponentAgent decOutputQuantityAgent = desktop.query("#incDetails").query("decimalbox").query("#decOutputQuantity");
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
					45);
			
			addOutput(desktop,
					"mmaterial8 : mm8",
					new BigDecimal(2000),
					8,
					"ml : milliliter",
					40);
			
			addOutput(desktop,
					"mmaterial9 : mm9",
					new BigDecimal(5),
					1,
					"pcs : piece",
					15);
			
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
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnInputRowDeletion" + "mm6");
			btnInputRowDeletion.click();
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm4");
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
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnInputRowDeletion" + "mm6");
			btnInputRowDeletion.click();
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm4");
			btnOutputRowDeletion.click();
			
			//update weights to sum 100%
			btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm2");
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
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm6");
			btnOutputRowDeletion.click();
			
			//update weights to sum 100%
			btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm7");
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
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnInputRowDeletion" + "mm7");
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
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnInputRowDeletion" + "mm2");
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
			
			ComponentAgent btnInputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnInputRowDeletion" + "mm2");
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
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm5");
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
			
			ComponentAgent btnOutputRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnOutputRowDeletion" + "mm9");
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
}
