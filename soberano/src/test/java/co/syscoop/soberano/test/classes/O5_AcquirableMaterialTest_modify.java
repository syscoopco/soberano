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
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;

import co.syscoop.soberano.test.helper.AcquirableMaterialActionTest;
import co.syscoop.soberano.test.helper.AcquirableMaterialForm;

@Order(5)

@Disabled

class O5_AcquirableMaterialTest_modify extends AcquirableMaterialActionTest{

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
																//to runtime locale not available under 
																//testing environment
		//Zats.init("./src/main/webapp");	
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		Zats.cleanup();
		//Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testCase1() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user1@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material1 : m1");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm1");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial1");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(12800.00128));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((1)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase2() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user2@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material2 : m2");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm2");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial2");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((2)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase3() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user3@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material2 : m2");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm2");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial2");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((2)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase4() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user4@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material3 : m3");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm3");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial3");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2.5));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((6)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase5() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user5@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material3 : m3");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm3");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial3");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2.5));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((6)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase6() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user6@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material4 : m4");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm4");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial4");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((4)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase7() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user7@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material4 : m4");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm4");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial4");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((4)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase8() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user8@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material5 : m5");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm5");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial5");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(4));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((5)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase9() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user9@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material5 : m5");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm5");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial5");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(4));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((5)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase10() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user10@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material6 : m6");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm6");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial6");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(400.00004));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((4)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase11() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user11@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material6 : m6");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm6");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial6");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(400.00004));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((4)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase12() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user12@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material7 : m7");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm7");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial7");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((8)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase13() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user13@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material7 : m7");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm7");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial7");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((8)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase14() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user14@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material8 : m8");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm8");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial8");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(3));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((8)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase15() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user15@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material8 : m8");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm8");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial8");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(3));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((8)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase16() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user16@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material9 : m9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm9");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100.00001));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((1)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
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
	final void testCase17() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user17@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("material9 : m9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm9");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100.00001));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((1)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase18() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user1@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("mmaterial9 : mm9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm1");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial93");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(12800.00128));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((1)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
	
	@Test
	final void testCase19() {
	
		try {
			AcquirableMaterialForm acquirableMaterialForm = setFormComponents("user1@soberano.syscoop.co", "acquirable_materials.zul");			
			loadObjectDetails("mmaterial9 : mm9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "mm93");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "mmaterial1");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(12800.00128));
			ComponentAgent cmbUnitAgent = acquirableMaterialForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), Integer.valueOf((1)));
			
			clickOnApplyButton(acquirableMaterialForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
}
