package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.Zats;

import co.syscoop.soberano.test.helper.ProviderActionTest;
import co.syscoop.soberano.test.helper.ProviderForm;

@Order(5)

//TODO: enable test
@Disabled

class O5_ProviderTest_modify extends ProviderActionTest{

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
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
	final void testCase1() {
	
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov1");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov1");
			clickOnApplyButton(providerForm.getDesktop());
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
			ProviderForm providerForm = setFormComponents("user2@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov2");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov2");
			clickOnApplyButton(providerForm.getDesktop());
			
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
			ProviderForm providerForm = setFormComponents("user3@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov2");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov2");
			clickOnApplyButton(providerForm.getDesktop());
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
			ProviderForm providerForm = setFormComponents("user4@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov3");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov3");
			clickOnApplyButton(providerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase5() {
	
		try {
			ProviderForm providerForm = setFormComponents("user5@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov4");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov4");
			clickOnApplyButton(providerForm.getDesktop());
			
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
	final void testCase6() {
	
		try {
			ProviderForm providerForm = setFormComponents("user6@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov4");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov4");
			clickOnApplyButton(providerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase7() {
	
		try {
			ProviderForm providerForm = setFormComponents("user7@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov5");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov5");
			clickOnApplyButton(providerForm.getDesktop());
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
			ProviderForm providerForm = setFormComponents("user8@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov6");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov6");
			clickOnApplyButton(providerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase9() {
	
		try {
			ProviderForm providerForm = setFormComponents("user9@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov7");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov7");
			clickOnApplyButton(providerForm.getDesktop());
			
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
	final void testCase10() {
	
		try {
			ProviderForm providerForm = setFormComponents("user10@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov7");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov7");
			clickOnApplyButton(providerForm.getDesktop());
			
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
			ProviderForm providerForm = setFormComponents("user11@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov7");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov7");
			clickOnApplyButton(providerForm.getDesktop());
			
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
	final void testCase12() {
	
		try {
			ProviderForm providerForm = setFormComponents("user12@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov7");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov7");
			clickOnApplyButton(providerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase13() {
	
		try {
			ProviderForm providerForm = setFormComponents("user13@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("prov8");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov8");
			clickOnApplyButton(providerForm.getDesktop());
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
			ProviderForm providerForm = setFormComponents("user14@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("mprov8");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov9");
			clickOnApplyButton(providerForm.getDesktop());
			
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
			ProviderForm providerForm = setFormComponents("user15@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("mprov8");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov9");
			clickOnApplyButton(providerForm.getDesktop());
			
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
	final void testCase16() {
	
		try {
			ProviderForm providerForm = setFormComponents("user16@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("mprov8");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov9");
			clickOnApplyButton(providerForm.getDesktop());
			
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
			ProviderForm providerForm = setFormComponents("user17@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("mprov8");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov9");
			clickOnApplyButton(providerForm.getDesktop());
			
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
	final void testCase18() {
	
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");			
			loadObjectDetails("mprov1");			
			providerForm.setComponentValue(providerForm.getTxtName(), "mprov8");
			clickOnApplyButton(providerForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
	
	protected void checkProvider(ProviderForm providerForm, String name) {

		String qualifiedName = name;
		loadObjectDetails(qualifiedName);		
		assertEquals(name.toLowerCase(), providerForm.getTxtName().getText().toLowerCase(), "Wrong name shown for provider " +  qualifiedName);
	}
	
	@Test
	final void testCase19() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov1");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase20() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov2");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase21() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov3");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase22() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov4");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase23() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov5");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase24() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov6");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase25() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov7");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase26() {
		
		try {
			ProviderForm providerForm = setFormComponents("user1@soberano.syscoop.co", "providers.zul");		
			checkProvider(providerForm, "mprov8");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}